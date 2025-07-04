package com.newton.domain.models.auth

data class VerifyOtpData(
    val email: String,
    val otp: String,
)
