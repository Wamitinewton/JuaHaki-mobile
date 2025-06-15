package com.newton.auth.presentation.signup.event

sealed class SignupUiEffect {
    object NavigateToLogin : SignupUiEffect()
    object NavigateToEmailVerification : SignupUiEffect()
    data class ShowSnackbar(val message: String) : SignupUiEffect()
    data class ShowToast(val message: String) : SignupUiEffect()
}