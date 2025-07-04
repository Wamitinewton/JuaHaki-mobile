package com.newton.domain.models.oauth

data class OAuthAuthorizationData(
    val authorizationUrl: String,
    val state: String,
    val codeChallenge: String,
    val codeVerifier: String,
)
