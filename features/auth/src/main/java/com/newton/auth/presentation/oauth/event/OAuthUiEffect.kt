package com.newton.auth.presentation.oauth.event


import com.newton.core.utils.SnackbarUiEffect

sealed class OAuthUiEffect : SnackbarUiEffect {
    data class LaunchOAuthFlow(val authorizationUrl: String) : OAuthUiEffect()
    data object NavigateToHome : OAuthUiEffect()
    data class ShowOAuthError(val message: String) : OAuthUiEffect()
}