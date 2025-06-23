package com.newton.auth.presentation.forgotpassword.state

import com.newton.core.utils.ValidationError

data class InitiatePasswordResetUiState(
    val email: String = "",
    val emailError: ValidationError? = null,
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
    val initiateSuccess: Boolean = false,
    val initiateError: String? = null,
) {
    fun hasAnyError(): Boolean = emailError != null

    fun areAllFieldsFilled(): Boolean = email.isNotBlank()
}
