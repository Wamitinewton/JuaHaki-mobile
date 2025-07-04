package com.newton.domain.models.oauth

data class OAuthTokenRequest(
    val code: String,
    val codeVerifier: String,
    val state: String,
)
