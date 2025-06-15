package com.newton.auth.presentation.login.state

import com.newton.domain.models.auth.JwtData

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isFormValid: Boolean = false,
    val loginSuccess: Boolean = false,
    val loginError: String? = null,
    val jwtData: JwtData? = null,
) {
    fun areAllFieldsFilled(): Boolean =
        email.isNotBlank() && password.isNotBlank()
}
