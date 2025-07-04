package com.newton.auth.presentation.forgotpassword.event

import com.newton.core.utils.SnackbarUiEffect

sealed class ResetPasswordUiEffect : SnackbarUiEffect {
    object NavigateToLogin : ResetPasswordUiEffect()

    object NavigateToInitiatePasswordReset : ResetPasswordUiEffect()
}
