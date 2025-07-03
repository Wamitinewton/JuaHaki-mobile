package com.newton.data.dto.oauth

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationUrlResponse(
    val authorizationUrl: String,
    val state: String,
    val codeChallenge: String
)