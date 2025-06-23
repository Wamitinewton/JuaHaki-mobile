package com.newton.auth.presentation.forgotpassword.state

import com.newton.core.utils.ValidationError

data class ResetPasswordUiState(
    val email: String = "",
    val otp: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val otpError: ValidationError? = null,
    val newPasswordError: ValidationError? = null,
    val confirmPasswordError: ValidationError? = null,
    val isLoading: Boolean = false,
    val isNewPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isFormValid: Boolean = false,
    val resetSuccess: Boolean = false,
    val resetError: String? = null,
) {
    fun hasAnyError(): Boolean =
        otpError != null ||
                newPasswordError != null ||
                confirmPasswordError != null

    fun areAllFieldsFilled(): Boolean =
        otp.isNotBlank() &&
                newPassword.isNotBlank() &&
                confirmPassword.isNotBlank()
}
