package com.newton.auth.presentation.oauth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.oauth.event.OAuthUiEffect
import com.newton.auth.presentation.oauth.event.OAuthUiEvent
import com.newton.auth.presentation.oauth.state.OAuthUiState
import com.newton.core.enums.OAuthProvider
import com.newton.core.extensions.sendErrorSnackbar
import com.newton.core.extensions.sendInfoSnackbar
import com.newton.core.extensions.sendSuccessSnackbar
import com.newton.domain.models.oauth.OAuthTokenRequest
import com.newton.domain.models.oauth.PKCEData
import com.newton.domain.repository.oauth.OAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OAuthViewModel
    @Inject
    constructor(
        private val oAuthRepository: OAuthRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(OAuthUiState())
        val uiState: StateFlow<OAuthUiState> = _uiState.asStateFlow()

        private val _uiEffect = Channel<OAuthUiEffect>()
        val uiEffect = _uiEffect.receiveAsFlow()

        /**
         * Handles UI events
         */
        fun onEvent(event: OAuthUiEvent) {
            when (event) {
                is OAuthUiEvent.OnOAuthSignInClicked -> initiateOAuthFlow(event.provider)
                OAuthUiEvent.OnOAuthRetry -> retryLastOAuthFlow()
                OAuthUiEvent.OnClearError -> clearError()
            }
        }

        /**
         * Initiates OAuth flow for the specified provider
         */
        private fun initiateOAuthFlow(provider: OAuthProvider) {
            viewModelScope.launch {
                try {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = true,
                            currentProvider = provider,
                            error = null,
                        )

                    _uiEffect.sendInfoSnackbar("Preparing ${provider.providerName} sign-in...")

                    // Generate PKCE data
                    val pkceData = oAuthRepository.generatePKCEData()

                    // Store PKCE data for later use
                    oAuthRepository.storePKCEData(pkceData)

                    Timber.d("Generated PKCE data for ${provider.providerName} OAuth")

                    // Get authorization URL
                    oAuthRepository
                        .getAuthorizationUrl(
                            provider = provider,
                            codeVerifier = pkceData.codeVerifier,
                            state = pkceData.state,
                        ).collect { resource ->
                            resource.handle(
                                onLoading = { isLoading ->
                                    _uiState.value = _uiState.value.copy(isLoading = isLoading)
                                },
                                onSuccess = { authData ->
                                    _uiState.value =
                                        _uiState.value.copy(
                                            isLoading = false,
                                            authorizationUrl = authData?.authorizationUrl,
                                            error = null,
                                        )

                                    authData?.let {
                                        Timber.d("Got authorization URL, launching OAuth flow")
                                        _uiEffect.send(OAuthUiEffect.LaunchOAuthFlow(it.authorizationUrl))
                                    }
                                },
                                onError = { message, _, _ ->
                                    _uiState.value =
                                        _uiState.value.copy(
                                            isLoading = false,
                                            error = message,
                                        )

                                    oAuthRepository.clearPKCEData()

                                    Timber.e("Failed to get authorization URL: $message")
                                    _uiEffect.sendErrorSnackbar(
                                        message = message ?: "Failed to start OAuth flow",
                                        actionLabel = "Retry",
                                        onActionClick = { retryLastOAuthFlow() },
                                    )
                                },
                            )
                        }
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            error = e.localizedMessage,
                        )

                    oAuthRepository.clearPKCEData()

                    Timber.e(e, "OAuth flow initiation failed")
                    _uiEffect.sendErrorSnackbar(
                        message = "Failed to start OAuth flow: ${e.localizedMessage}",
                        actionLabel = "Try Again",
                        onActionClick = { retryLastOAuthFlow() },
                    )
                }
            }
        }

        /**
         * Exchanges authorization code for tokens
         */
        fun exchangeCodeForTokens(
            provider: OAuthProvider,
            tokenRequest: OAuthTokenRequest,
            onComplete: (Boolean, String?) -> Unit,
        ) {
            viewModelScope.launch {
                try {
                    _uiState.value =
                        _uiState.value.copy(
                            isAuthenticating = true,
                            error = null,
                        )

                    _uiEffect.sendInfoSnackbar("Completing ${provider.providerName} sign-in...")

                    oAuthRepository.exchangeCodeForTokens(provider, tokenRequest).collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value = _uiState.value.copy(isAuthenticating = isLoading)
                            },
                            onSuccess = { jwtData ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isAuthenticating = false,
                                        error = null,
                                    )

                                // Clear PKCE data after successful authentication
                                oAuthRepository.clearPKCEData()

                                Timber.d("OAuth authentication successful for user: ${jwtData?.userInfo?.username}")

                                _uiEffect.send(OAuthUiEffect.NavigateToHome)
                                _uiEffect.sendSuccessSnackbar(
                                    message = "Welcome! Signed in successfully with ${provider.providerName}",
                                    actionLabel = "OK",
                                )

                                onComplete(true, "Authentication successful")
                            },
                            onError = { message, _, _ ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isAuthenticating = false,
                                        error = message,
                                    )

                                // Clear PKCE data on error
                                oAuthRepository.clearPKCEData()

                                Timber.e("OAuth token exchange failed: $message")

                                _uiEffect.sendErrorSnackbar(
                                    message = message ?: "Authentication failed",
                                    actionLabel = "Try Again",
                                    onActionClick = { retryLastOAuthFlow() },
                                )

                                onComplete(false, message ?: "Authentication failed")
                            },
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isAuthenticating = false,
                            error = e.localizedMessage,
                        )

                    // Clear PKCE data on exception
                    oAuthRepository.clearPKCEData()

                    Timber.e(e, "OAuth token exchange exception")

                    _uiEffect.sendErrorSnackbar(
                        message = "Authentication failed: ${e.localizedMessage}",
                        actionLabel = "Try Again",
                        onActionClick = { retryLastOAuthFlow() },
                    )

                    onComplete(false, e.localizedMessage)
                }
            }
        }

        /**
         * Gets stored PKCE data
         */
        suspend fun getPKCEData(): PKCEData? =
            try {
                oAuthRepository.getPKCEData()
            } catch (e: Exception) {
                Timber.e(e, "Failed to get PKCE data")
                null
            }

        /**
         * Retries the last OAuth flow
         */
        private fun retryLastOAuthFlow() {
            val currentProvider = _uiState.value.currentProvider
            if (currentProvider != null) {
                initiateOAuthFlow(currentProvider)
            }
        }

        /**
         * Clears error state
         */
        private fun clearError() {
            _uiState.value = _uiState.value.copy(error = null)
        }

        /**
         * Resets OAuth state
         */
        fun resetOAuthState() {
            viewModelScope.launch {
                oAuthRepository.clearPKCEData()
                _uiState.value = OAuthUiState()
            }
        }

        override fun onCleared() {
            super.onCleared()
            viewModelScope.launch {
                oAuthRepository.clearPKCEData()
            }
        }
    }
