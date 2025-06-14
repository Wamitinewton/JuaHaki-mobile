package com.newton.domain.models.auth

data class JwtData(
    val accessToken: String,
    val refreshToken: String,
    val userInfo: UserInfo,
)
