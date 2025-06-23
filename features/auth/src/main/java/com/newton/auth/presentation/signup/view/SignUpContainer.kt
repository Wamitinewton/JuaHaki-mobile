package com.newton.auth.presentation.signup.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.auth.presentation.signup.event.SignupUiEffect
import com.newton.auth.presentation.signup.viewmodel.SignupViewModel
import com.newton.commonui.ui.HandleUiEffects
import com.newton.commonui.ui.SnackbarData

@Composable
fun SignUpContainer(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToEmailVerification: (String) -> Unit,
    onSignUpWithGoogle: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit,
    onShowSnackbar: (SnackbarData) -> Unit,
    onShowToast: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    HandleUiEffects(
        uiEffects = viewModel.uiEffect,
        onShowSnackbar = onShowSnackbar,
        onShowToast = onShowToast,
        onCustomEffect = { effect ->
            when (effect) {
                is SignupUiEffect.NavigateToLogin -> {
                    onNavigateToLogin()
                }
                is SignupUiEffect.NavigateToEmailVerification -> {
                    onNavigateToEmailVerification(effect.email)
                }
            }
        }
    )

    SignUpScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack,
        onNavigateToLogin = onNavigateToLogin,
        onSignUpWithGoogle = onSignUpWithGoogle,
        onPrivacyPolicyClick = onPrivacyPolicyClick,
        onTermsOfServiceClick = onTermsOfServiceClick,
        modifier = modifier,
    )
}
