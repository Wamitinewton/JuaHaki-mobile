package com.newton.auth.presentation.forgotpassword.event

import com.newton.core.utils.SnackbarUiEffect

sealed class InitiatePasswordResetUiEffect : SnackbarUiEffect {
    object NavigateToLogin : InitiatePasswordResetUiEffect()

    data class NavigateToResetPassword(
        val email: String,
    ) : InitiatePasswordResetUiEffect()
}
