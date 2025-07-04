package com.newton.domain.models.oauth

data class PKCEData(
    val codeVerifier: String,
    val codeChallenge: String,
    val state: String,
)
