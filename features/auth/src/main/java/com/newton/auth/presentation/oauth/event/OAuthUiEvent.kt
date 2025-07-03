package com.newton.auth.presentation.oauth.event

import com.newton.core.enums.OAuthProvider

sealed class OAuthUiEvent {
    data class OnOAuthSignInClicked(val provider: OAuthProvider) : OAuthUiEvent()
    data object OnOAuthRetry : OAuthUiEvent()
    data object OnClearError : OAuthUiEvent()
}
