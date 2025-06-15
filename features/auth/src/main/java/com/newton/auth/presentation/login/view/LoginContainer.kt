package com.newton.auth.presentation.login.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.auth.presentation.login.event.LoginUiEffect
import com.newton.auth.presentation.login.viewmodel.LoginViewModel
import com.newton.commonui.ui.HandleUiEffects
import com.newton.commonui.ui.SnackbarData

@Composable
fun LoginContainer(
    onNavigateBack: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onLoginWithGoogle: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit,
    onShowSnackbar: (SnackbarData) -> Unit,
    onShowToast: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HandleUiEffects(
        uiEffects = viewModel.uiEffect,
        onShowSnackbar = onShowSnackbar,
        onShowToast = onShowToast,
        onCustomEffect = { effect ->
            when (effect) {
                is LoginUiEffect.NavigateToSignup -> {
                    onNavigateToSignUp()
                }
                is LoginUiEffect.NavigateToHome -> {
                    onNavigateToHome()
                }
                is LoginUiEffect.NavigateToForgotPassword -> {
                    onNavigateToForgotPassword()
                }
            }
        },
    )

    LoginScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack,
        onNavigateToSignUp = onNavigateToSignUp,
        onLoginWithGoogle = onLoginWithGoogle,
        onPrivacyPolicyClick = onPrivacyPolicyClick,
        onTermsOfServiceClick = onTermsOfServiceClick,
        modifier = modifier,
    )
}
