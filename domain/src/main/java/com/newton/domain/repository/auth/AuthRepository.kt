package com.newton.domain.repository.auth

import com.newton.core.utils.Resource
import com.newton.domain.models.auth.JwtData
import com.newton.domain.models.auth.LoginData
import com.newton.domain.models.auth.RefreshTokenData
import com.newton.domain.models.auth.SignupData
import com.newton.domain.models.auth.UserInfo
import com.newton.domain.models.auth.VerifyOtpData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun createUser(request: SignupData): Flow<Resource<UserInfo>>

    suspend fun login(request: LoginData): Flow<Resource<JwtData>>

    suspend fun refreshToken(request: RefreshTokenData): Flow<Resource<JwtData>>

    suspend fun activateUserAccount(request: VerifyOtpData): Flow<Resource<Unit>>

    suspend fun resendEmailVerificationOtp(email: String): Flow<Resource<Unit>>

    suspend fun storeSessionTokens(
        accessToken: String,
        refreshToken: String,
    )

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    suspend fun storeLoggedInUser(user: UserInfo)

    suspend fun logout(): Flow<Resource<Unit>>

    suspend fun initiatePasswordReset(email: String): Flow<Resource<Unit>>

    suspend fun resetPassword(
        email: String,
        otp: String,
        newPassword: String,
    ): Flow<Resource<Unit>>
}
