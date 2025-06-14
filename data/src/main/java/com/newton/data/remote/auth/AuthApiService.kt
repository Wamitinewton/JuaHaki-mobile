package com.newton.data.remote.auth

import com.newton.data.dto.auth.LoginRequest
import com.newton.data.dto.auth.JwtResponse
import com.newton.data.dto.auth.RefreshTokenRequest
import com.newton.data.dto.auth.SignupRequest
import com.newton.data.dto.auth.UserDto
import com.newton.data.dto.auth.VerifyOtpRequest
import com.newton.data.remote.ApiConstants
import com.newton.data.remote.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST(ApiConstants.SIGN_UP)
    suspend fun createUser(
        @Body request: SignupRequest
    ) : ApiResponse<UserDto>

    @POST(ApiConstants.LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ): ApiResponse<JwtResponse>

    @POST(ApiConstants.REFRESH_TOKEN)
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): ApiResponse<JwtResponse>

    @POST(ApiConstants.VERIFY_EMAIL)
    suspend fun verifyEmail(
        @Body request: VerifyOtpRequest
    ): ApiResponse<Unit>

    @POST(ApiConstants.RESEND_EMAIL_VERIFICATION)
    suspend fun resendEmailVerification(
        @Query("email") email: String
    ): ApiResponse<Unit>
}