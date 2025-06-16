package com.newton.data.dto.auth

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDto
)
