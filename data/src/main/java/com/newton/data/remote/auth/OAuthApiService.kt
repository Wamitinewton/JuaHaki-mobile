package com.newton.data.remote.auth

import com.newton.data.dto.auth.JwtResponse
import com.newton.data.dto.oauth.AuthorizationUrlResponse
import com.newton.data.remote.ApiResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OAuthApiService {

    @GET("/api/v1/auth/oauth2/authorize-url")
    suspend fun getAuthorizationUrl(
        @Query("provider") provider: String,
        @Query("codeVerifier") codeVerifier: String,
        @Query("state") state: String
    ): ApiResponse<AuthorizationUrlResponse>

    @POST("/api/v1/auth/oauth2/callback/{provider}")
    suspend fun exchangeCodeForTokens(
        @Path("provider") provider: String,
        @Query("code") code: String,
        @Query("state") state: String,
        @Query("client") client: String = "android"
    ): ApiResponse<JwtResponse>
}