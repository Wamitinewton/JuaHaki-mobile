package com.newton.domain.models.auth

data class LoginData(
    val usernameOrEmail: String,
    val password: String,
)
