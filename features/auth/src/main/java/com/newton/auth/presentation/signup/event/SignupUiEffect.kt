package com.newton.auth.presentation.signup.event

import com.newton.core.utils.SnackbarUiEffect

sealed class SignupUiEffect : SnackbarUiEffect {
    object NavigateToLogin : SignupUiEffect()

    data class NavigateToEmailVerification(
        val email: String,
    ) : SignupUiEffect()
}
