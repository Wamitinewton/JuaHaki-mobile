package com.newton.auth.presentation.signup.event

import androidx.compose.material3.SnackbarDuration
import com.newton.core.enums.SnackbarType

sealed class SignupUiEffect {
    object NavigateToLogin : SignupUiEffect()
    object NavigateToEmailVerification : SignupUiEffect()
    data class ShowSnackbar(
        val message: String,
        val type: SnackbarType = SnackbarType.INFO,
        val duration: SnackbarDuration = SnackbarDuration.Short,
        val actionLabel: String? = null,
        val onActionClick: (() -> Unit)? = null
    ) : SignupUiEffect()
    data class ShowToast(val message: String) : SignupUiEffect()
}