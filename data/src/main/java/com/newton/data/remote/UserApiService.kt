package com.newton.data.remote

import com.newton.data.remote.utils.ApiConstants
import com.newton.data.remote.utils.ApiResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @POST(ApiConstants.INITIATE_RESET_PASSWORD)
    suspend fun initiatePasswordReset(
        @Query("email") email: String,
    ): ApiResponse<Unit>

    @POST(ApiConstants.RESET_PASSWORD)
    suspend fun resetPassword(
        @Query("email") email: String,
        @Query("otp") otp: String,
        @Query("newPassword") newPassword: String,
    ): ApiResponse<Unit>
}
