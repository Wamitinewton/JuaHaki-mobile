package com.newton.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val username: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
)
