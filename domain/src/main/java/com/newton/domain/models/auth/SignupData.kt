package com.newton.domain.models.auth

data class SignupData(
    val firstName: String,
    val lastName: String,
    val username: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
)
