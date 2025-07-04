package com.newton.auth.presentation.login.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.auth.manager.CustomTabsManager
import com.newton.auth.presentation.login.event.LoginUiEffect
import com.newton.auth.presentation.login.viewmodel.LoginViewModel
import com.newton.auth.presentation.oauth.event.OAuthUiEffect
import com.newton.auth.presentation.oauth.viewmodel.OAuthViewModel
import com.newton.commonui.ui.HandleUiEffects
import com.newton.commonui.ui.SnackbarData

@Composable
fun LoginContainer(
    onNavigateBack: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToActivateAccount: (String) -> Unit,
    onShowSnackbar: (SnackbarData) -> Unit,
    onShowToast: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    oAuthViewModel: OAuthViewModel = hiltViewModel(),
    customTabsManager: CustomTabsManager = CustomTabsManager(),
) {
    val context = LocalContext.current
    val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val oAuthUiState by oAuthViewModel.uiState.collectAsStateWithLifecycle()

    HandleUiEffects(
        uiEffects = loginViewModel.uiEffect,
        onShowSnackbar = onShowSnackbar,
        onShowToast = onShowToast,
        onCustomEffect = { effect ->
            when (effect) {
                is LoginUiEffect.NavigateToSignup -> onNavigateToSignUp()
                is LoginUiEffect.NavigateToHome -> onNavigateToHome()
                is LoginUiEffect.NavigateToForgotPassword -> onNavigateToForgotPassword()
                is LoginUiEffect.NavigateToVerification -> onNavigateToActivateAccount(effect.email)
            }
        },
    )

    HandleUiEffects(
        uiEffects = oAuthViewModel.uiEffect,
        onShowSnackbar = onShowSnackbar,
        onShowToast = onShowToast,
        onCustomEffect = { effect ->
            when (effect) {
                is OAuthUiEffect.LaunchOAuthFlow -> {
                    customTabsManager.launchOAuthFlow(context, effect.authorizationUrl)
                }
                is OAuthUiEffect.NavigateToHome -> onNavigateToHome()
                is OAuthUiEffect.ShowOAuthError -> {
                }
            }
        },
    )

    LoginScreen(
        loginUiState = loginUiState,
        oAuthUiState = oAuthUiState,
        onLoginEvent = loginViewModel::onEvent,
        onOAuthEvent = oAuthViewModel::onEvent,
        onNavigateBack = onNavigateBack,
        onNavigateToSignUp = onNavigateToSignUp,
        modifier = modifier,
    )
}
