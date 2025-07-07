package com.newton.data.remote.utils

data class ApiResponse<T>(
    val message: String,
    val data: T? = null,
)
