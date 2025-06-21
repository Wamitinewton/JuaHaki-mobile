package com.newton.auth.presentation.login.event

import com.newton.core.utils.SnackbarUiEffect

sealed class LoginUiEffect: SnackbarUiEffect {
    object NavigateToSignup : LoginUiEffect()
    object NavigateToHome : LoginUiEffect()
    object NavigateToForgotPassword : LoginUiEffect()
    data class NavigateToVerification(val email: String): LoginUiEffect()
}