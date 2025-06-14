package com.newton.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpRequest(
    val email: String,
    val otp: String,
)
