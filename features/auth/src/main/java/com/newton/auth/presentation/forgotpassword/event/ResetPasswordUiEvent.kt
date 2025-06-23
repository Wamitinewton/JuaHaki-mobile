package com.newton.auth.presentation.forgotpassword.event

sealed class ResetPasswordUiEvent {
    data class OnEmailChanged(
        val email: String,
    ) : ResetPasswordUiEvent()

    data class OnOtpChanged(
        val otp: String,
    ) : ResetPasswordUiEvent()

    data class OnNewPasswordChanged(
        val newPassword: String,
    ) : ResetPasswordUiEvent()

    data class OnConfirmPasswordChanged(
        val confirmPassword: String,
    ) : ResetPasswordUiEvent()

    object OnToggleNewPasswordVisibility : ResetPasswordUiEvent()

    object OnToggleConfirmPasswordVisibility : ResetPasswordUiEvent()

    object OnResetPasswordClicked : ResetPasswordUiEvent()

    object OnClearError : ResetPasswordUiEvent()

    object OnNavigateBack : ResetPasswordUiEvent()

    object OnValidateNewPassword : ResetPasswordUiEvent()

    object OnValidateAllFields : ResetPasswordUiEvent()

    object OnValidateOtp : ResetPasswordUiEvent()

    object OnValidateConfirmPassword : ResetPasswordUiEvent()
}
