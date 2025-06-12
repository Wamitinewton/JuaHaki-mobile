package com.newton.auth.presentation.otp.state

data class OtpVerificationScreenState(
    val otpCode: String = "",
    val isLoading: Boolean = false,
    val isResendingCode: Boolean = false,
    val isFormValid: Boolean = false,
    val errorMessage: String? = null,
    val resendCooldownSeconds: Int = 0,
    val canResend: Boolean = true,
    val otpLength: Int = 6,
) {
    val isResendButtonEnabled: Boolean
        get() = canResend && !isResendingCode && !isLoading && resendCooldownSeconds == 0
}

