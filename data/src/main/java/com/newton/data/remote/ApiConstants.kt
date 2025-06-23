package com.newton.data.remote

object ApiConstants {
    const val SIGN_UP = "/api/v1/auth/signup"
    const val LOGIN = "/api/v1/auth/login"
    const val REFRESH_TOKEN = "/api/v1/auth/refresh"
    const val VERIFY_EMAIL = "/api/v1/auth/verify-email"
    const val RESEND_EMAIL_VERIFICATION = "/api/v1/auth/resend-verification"

    // User Management
    const val INITIATE_RESET_PASSWORD = "/api/v1/user/password/reset/initiate"
    const val RESET_PASSWORD = "/api/v1/user/password/reset/confirm"
}