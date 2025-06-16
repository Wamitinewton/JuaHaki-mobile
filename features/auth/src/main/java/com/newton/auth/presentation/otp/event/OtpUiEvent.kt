package com.newton.auth.presentation.otp.event

sealed class OtpUiEvent {
    data class OnOtpChanged(
        val otp: String,
    ) : OtpUiEvent()

    data class OnEmailChanged(
        val email: String,
    ) : OtpUiEvent()

    object OnVerifyOtpClicked : OtpUiEvent()

    object OnResendOtpClicked : OtpUiEvent()

    object OnClearError : OtpUiEvent()

    object OnNavigateBack : OtpUiEvent()

    object OnNavigateToLogin : OtpUiEvent()
}