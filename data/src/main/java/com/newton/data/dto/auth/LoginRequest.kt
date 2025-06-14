package com.newton.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val usernameOrEmail: String,
    val password: String,
)
