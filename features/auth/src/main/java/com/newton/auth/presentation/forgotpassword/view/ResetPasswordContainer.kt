package com.newton.auth.presentation.forgotpassword.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.auth.presentation.forgotpassword.event.ResetPasswordUiEffect
import com.newton.auth.presentation.forgotpassword.viewmodel.ResetPasswordViewModel
import com.newton.commonui.ui.HandleUiEffects
import com.newton.commonui.ui.SnackbarData

@Composable
fun ResetPasswordContainer(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToInitiatePasswordReset: () -> Unit,
    onShowSnackbar: (SnackbarData) -> Unit,
    onShowToast: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
    viewModel: ResetPasswordViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HandleUiEffects(
        uiEffects = viewModel.uiEffect,
        onShowSnackbar = onShowSnackbar,
        onShowToast = onShowToast,
        onCustomEffect = { effect ->
            when (effect) {
                is ResetPasswordUiEffect.NavigateToLogin -> {
                    onNavigateToLogin()
                }
                is ResetPasswordUiEffect.NavigateToInitiatePasswordReset -> {
                    onNavigateToInitiatePasswordReset()
                }
            }
        },
    )

    ResetPasswordScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}