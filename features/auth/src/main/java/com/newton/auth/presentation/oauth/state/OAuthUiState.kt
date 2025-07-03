package com.newton.auth.presentation.oauth.state

import com.newton.core.enums.OAuthProvider


data class OAuthUiState(
    val isLoading: Boolean = false,
    val currentProvider: OAuthProvider? = null,
    val authorizationUrl: String? = null,
    val error: String? = null,
    val isAuthenticating: Boolean = false
)