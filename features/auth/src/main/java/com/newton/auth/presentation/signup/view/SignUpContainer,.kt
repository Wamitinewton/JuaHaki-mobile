package com.newton.auth.presentation.signup.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.auth.presentation.signup.event.SignupUiEffect
import com.newton.auth.presentation.signup.event.SignupUiEvent
import com.newton.auth.presentation.signup.viewmodel.SignupViewModel

@Composable
fun SignUpContainer(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToEmailVerification: () -> Unit,
    onSignUpWithGoogle: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit,
    onShowSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is SignupUiEffect.NavigateToLogin -> onNavigateToLogin()
                is SignupUiEffect.NavigateToEmailVerification -> onNavigateToEmailVerification()
                is SignupUiEffect.ShowSnackbar -> {
                    onShowSnackbar(effect.message)
                    viewModel.onEvent(SignupUiEvent.OnClearError)
                }
                is SignupUiEffect.ShowToast -> null
            }
        }
    }

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
