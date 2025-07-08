package com.newton.auth.data.repository

import com.newton.auth.data.AuthTokenHolder
import com.newton.core.utils.Resource
import com.newton.data.mappers.toJwtData
import com.newton.data.mappers.toLoginRequest
import com.newton.data.mappers.toRefreshTokenRequest
import com.newton.data.mappers.toRequestDto
import com.newton.data.mappers.toUserDomain
import com.newton.data.mappers.toVerifyOtpRequest
import com.newton.data.remote.AuthApiService
import com.newton.data.remote.UserApiService
import com.newton.data.remote.utils.safeApiCall
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
        private val userApiService: UserApiService,
        private val sessionManager: SessionManager,
        private val userDao: UserDao,
    ) : AuthRepository {
        init {
            AuthTokenHolder.initializeTokens(sessionManager)
        }

        override suspend fun createUser(request: SignupData): Flow<Resource<UserInfo>> =
            safeApiCall(
                apiCall = {
                    val response = authApiService.createUser(request.toRequestDto())
                    response.data!!.toUserDomain()
                },
                errorHandler = { throwable ->
                    when (throwable) {
                        is IllegalStateException -> throwable.message ?: "User creation failed"
                        else -> "Failed to create user account. Please try again."
                    }
                },
            )

        override suspend fun login(request: LoginData): Flow<Resource<JwtData>> =
            safeApiCall(
                apiCall = {
                    val response = authApiService.login(request.toLoginRequest())

                    println("Login response: $response")
                    println("Response data: ${response.data}")

                    val jwtData = response.data!!.toJwtData()

                    storeSessionTokens(jwtData.accessToken, jwtData.refreshToken)
                    jwtData
                },
                errorHandler = { throwable ->
                    when (throwable) {
                        is IllegalStateException -> throwable.message ?: "Login failed"
                        else -> "Login failed. Please check your credentials and try again."
                    }
                },
            )

        override suspend fun refreshToken(request: RefreshTokenData): Flow<Resource<JwtData>> =
            safeApiCall(
                apiCall = {
                    val response = authApiService.refreshToken(request.toRefreshTokenRequest())
                    val jwtData = response.data!!.toJwtData()

                    storeSessionTokens(jwtData.accessToken, jwtData.refreshToken)
                    jwtData
                },
                errorHandler = { throwable ->
                    when (throwable) {
                        is IllegalStateException -> throwable.message ?: "Token refresh failed"
                        else -> "Session expired. Please log in again."
                    }
                },
            )

        override suspend fun activateUserAccount(request: VerifyOtpData): Flow<Resource<Unit>> =
            safeApiCall(
                apiCall = {
                    authApiService.verifyEmail(request.toVerifyOtpRequest())
                },
                errorHandler = {
                    "Account activation failed. Please check your verification code and try again."
                },
            )

        override suspend fun resendEmailVerificationOtp(email: String): Flow<Resource<Unit>> =
            safeApiCall(
                apiCall = {
                    authApiService.resendEmailVerification(email)
                },
                errorHandler = {
                    "Failed to resend verification code. Please try again later."
                },
            )

        override suspend fun storeSessionTokens(
            accessToken: String,
            refreshToken: String,
        ) {
            try {
                AuthTokenHolder.accessToken = accessToken
                AuthTokenHolder.refreshToken = refreshToken
                sessionManager.saveTokens(accessToken, refreshToken)
            } catch (e: Exception) {
                throw IllegalStateException("Failed to store authentication tokens: ${e.message}", e)
            }
        }

        override fun getAccessToken(): String? = sessionManager.fetchAccessToken()

        override fun getRefreshToken(): String? = sessionManager.fetchRefreshToken()

        override suspend fun storeLoggedInUser(user: UserInfo) = userDao.insertUser(user.toUserEntity())

        override suspend fun logout(): Flow<Resource<Unit>> =
            flow {
                emit(Resource.Loading(true))

                try {
                    AuthTokenHolder.accessToken = null
                    AuthTokenHolder.refreshToken = null

                    sessionManager.clearTokens()

                    emit(Resource.Success(Unit))
                } catch (e: Exception) {
                    emit(
                        Resource.Error(
                            message = "Logout failed: ${e.message ?: "Unknown error occurred"}",
                            errorType = com.newton.core.enums.ErrorType.UNKNOWN,
                        ),
                    )
                } finally {
                    emit(Resource.Loading(false))
                }
            }

        override suspend fun initiatePasswordReset(email: String): Flow<Resource<Unit>> =
            safeApiCall(
                apiCall = {
                    userApiService.initiatePasswordReset(email)
                },
                errorHandler = {
                    "Password Reset initiation Failed"
                },
            )

        override suspend fun resetPassword(
            email: String,
            otp: String,
            newPassword: String,
        ): Flow<Resource<Unit>> =
            safeApiCall(
                apiCall = {
                    userApiService.resetPassword(email, otp, newPassword)
                },
            )
    }
