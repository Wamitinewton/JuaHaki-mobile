package com.newton.auth.presentation.accountverification.event

import com.newton.core.utils.SnackbarUiEffect

sealed class OtpUiEffect: SnackbarUiEffect {
    object NavigateBack : OtpUiEffect()
    object NavigateToLogin : OtpUiEffect()
}