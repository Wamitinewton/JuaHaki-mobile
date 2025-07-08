package com.newton.auth.presentation.accountverification.state

data class AccountVerificationUiState(
    val email: String = "",
    val otp: String = "",
    val isLoading: Boolean = false,
    val isResendLoading: Boolean = false,
    val isFormValid: Boolean = false,
    val verificationSuccess: Boolean = false,
    val verificationError: String? = null,
    val resendSuccess: Boolean = false,
    val resendError: String? = null,
    val canResend: Boolean = true,
    val resendCooldownSeconds: Int = 0,
) {
    fun areAllFieldsFilled(): Boolean = email.isNotBlank() && otp.isNotBlank() && otp.length >= 6

    fun isOtpValid(): Boolean = otp.isNotBlank() && otp.all { it.isLetterOrDigit() } && otp.length in 4..6

    fun isEmailValid(): Boolean =
        email.isNotBlank() &&
            android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
}
