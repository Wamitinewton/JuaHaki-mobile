package com.newton.domain.models.auth

data class UserInfo(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
)
