package com.newton.auth.presentation.forgotpassword.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.auth.presentation.forgotpassword.event.InitiatePasswordResetUiEffect
import com.newton.auth.presentation.forgotpassword.viewmodel.InitiatePasswordResetViewModel
import com.newton.commonui.ui.HandleUiEffects
import com.newton.commonui.ui.SnackbarData

@Composable
fun ForgotPasswordContainer(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToResetPassword: (String) -> Unit,
    onShowSnackbar: (SnackbarData) -> Unit,
    onShowToast: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
    viewModel: InitiatePasswordResetViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HandleUiEffects(
        uiEffects = viewModel.uiEffect,
        onShowSnackbar = onShowSnackbar,
        onShowToast = onShowToast,
        onCustomEffect = { effect ->
            when (effect) {
                is InitiatePasswordResetUiEffect.NavigateToLogin -> {
                    onNavigateToLogin()
                }
                is InitiatePasswordResetUiEffect.NavigateToResetPassword -> {
                    onNavigateToResetPassword(effect.email)
                }
            }
        },
    )

    ForgotPasswordScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack,
        onNavigateToLogin = onNavigateToLogin,
        modifier = modifier,
    )
}
