package com.newton.data.remote

import com.newton.data.dto.auth.JwtResponse
import com.newton.data.dto.oauth.AuthorizationUrlResponse
import com.newton.data.remote.utils.ApiConstants
import com.newton.data.remote.utils.ApiResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OAuthApiService {
    @GET(ApiConstants.GET_AUTH_URL)
    suspend fun getAuthorizationUrl(
        @Query("provider") provider: String,
        @Query("codeVerifier") codeVerifier: String,
        @Query("state") state: String,
    ): ApiResponse<AuthorizationUrlResponse>

    @POST(ApiConstants.EXCHANGE_CODE_FOR_TOKENS)
    suspend fun exchangeCodeForTokens(
        @Path("provider") provider: String,
        @Query("code") code: String,
        @Query("state") state: String,
        @Query("client") client: String = "android",
    ): ApiResponse<JwtResponse>
}
