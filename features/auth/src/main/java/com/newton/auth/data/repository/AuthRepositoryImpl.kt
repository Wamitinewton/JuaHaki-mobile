package com.newton.auth.data.repository

import com.newton.auth.data.AuthTokenHolder
import com.newton.core.utils.Resource
import com.newton.data.mappers.toJwtData
import com.newton.data.mappers.toLoginRequest
import com.newton.data.mappers.toRefreshTokenRequest
import com.newton.data.mappers.toRequestDto
import com.newton.data.mappers.toUserDomain
import com.newton.data.mappers.toVerifyOtpRequest
import com.newton.data.remote.auth.AuthApiService
import com.newton.data.remote.safeApiCall
import com.newton.database.dao.UserDao
import com.newton.database.mappers.toUserEntity
import com.newton.database.sessionmanager.SessionManager
import com.newton.domain.models.auth.JwtData
import com.newton.domain.models.auth.LoginData
import com.newton.domain.models.auth.RefreshTokenData
import com.newton.domain.models.auth.SignupData
import com.newton.domain.models.auth.UserInfo
import com.newton.domain.models.auth.VerifyOtpData
import com.newton.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl
@Inject
constructor(
    private val authApiService: AuthApiService,
    private val sessionManager: SessionManager,
    private val userDao: UserDao,
): AuthRepository
{

    init {
        AuthTokenHolder.initializeTokens(sessionManager)
    }

    override suspend fun createUser(request: SignupData): Flow<Resource<UserInfo>> {
        return safeApiCall(
            apiCall = {
                val response = authApiService.createUser(request.toRequestDto())
                response.data?.toUserDomain()
                    ?: throw IllegalStateException("User data is null in the response")
            }
        )
    }

    override suspend fun login(request: LoginData): Flow<Resource<JwtData>> {
        return safeApiCall(
            apiCall = {
                val response = authApiService.login(request.toLoginRequest())
                response.data?.toJwtData()
                    ?: throw IllegalStateException("User data is null in the response")
            }
        )
    }

    override suspend fun refreshToken(request: RefreshTokenData): Flow<Resource<JwtData>> {
        return safeApiCall(
            apiCall = {
                val response = authApiService.refreshToken(request.toRefreshTokenRequest())
                val jwtData = response.data?.toJwtData()
                    ?: throw IllegalStateException("JWT data is null in the response")

                storeSessionTokens(jwtData.accessToken, jwtData.refreshToken)
                jwtData
            }
        )
    }

    override suspend fun activateUserAccount(request: VerifyOtpData): Flow<Resource<Unit>> {
        return safeApiCall(
            apiCall = {
                authApiService.verifyEmail(request.toVerifyOtpRequest())
            }
        )
    }

    override suspend fun resendEmailVerificationOtp(email: String): Flow<Resource<Unit>> {
        return safeApiCall(
            apiCall = {
                authApiService.resendEmailVerification(email)
            }
        )
    }

    override suspend fun storeSessionTokens(
        accessToken: String,
        refreshToken: String
    ) {
        try {
            AuthTokenHolder.accessToken = accessToken
            AuthTokenHolder.refreshToken = refreshToken
            sessionManager.saveTokens(accessToken, refreshToken)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getAccessToken(): String?  = sessionManager.fetchAccessToken()

    override fun getRefreshToken(): String? = sessionManager.fetchRefreshToken()

    override suspend fun storeLoggedInUser(user: UserInfo) {
        return userDao.insertUser(user.toUserEntity())
    }

    override suspend fun logout(): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading(true))

            AuthTokenHolder.accessToken = null
            AuthTokenHolder.refreshToken = null

            sessionManager.clearTokens()
        }
}