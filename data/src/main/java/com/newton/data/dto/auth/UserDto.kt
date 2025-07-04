package com.newton.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
)
