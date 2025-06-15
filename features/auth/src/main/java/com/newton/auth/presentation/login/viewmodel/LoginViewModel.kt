package com.newton.auth.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.login.event.LoginUiEffect
import com.newton.auth.presentation.login.event.LoginUiEvent
import com.newton.auth.presentation.login.state.LoginUiState
import com.newton.core.extensions.sendErrorSnackbar
import com.newton.core.extensions.sendInfoSnackbar
import com.newton.core.extensions.sendSuccessSnackbar
import com.newton.domain.models.auth.LoginData
import com.newton.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(LoginUiState())
        val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

        private val _uiEffect = Channel<LoginUiEffect>()
        val uiEffect = _uiEffect.receiveAsFlow()

        /**
         * Handles all UI events
         */
        fun onEvent(event: LoginUiEvent) {
            when (event) {
                is LoginUiEvent.OnEmailChanged -> updateEmail(event.email)
                is LoginUiEvent.OnPasswordChanged -> updatePassword(event.password)

                LoginUiEvent.OnTogglePasswordVisibility -> togglePasswordVisibility()
                LoginUiEvent.OnLoginClicked -> performLogin()
                LoginUiEvent.OnClearError -> clearError()
                LoginUiEvent.OnNavigateToSignup -> navigateToSignup()
                LoginUiEvent.OnNavigateToForgotPassword -> navigateToForgotPassword()
            }
        }

        private fun updateEmail(email: String) {
            _uiState.value =
                _uiState.value.copy(
                    email = email,
                    loginError = null,
                )
            updateFormValidity()
        }

        private fun updatePassword(password: String) {
            _uiState.value =
                _uiState.value.copy(
                    password = password,
                    loginError = null,
                )
            updateFormValidity()
        }

        private fun togglePasswordVisibility() {
            _uiState.value =
                _uiState.value.copy(
                    isPasswordVisible = !_uiState.value.isPasswordVisible,
                )
        }

        private fun clearError() {
            _uiState.value =
                _uiState.value.copy(
                    loginError = null,
                )
        }

        private fun navigateToSignup() {
            viewModelScope.launch {
                _uiEffect.send(LoginUiEffect.NavigateToSignup)
            }
        }

        private fun navigateToForgotPassword() {
            viewModelScope.launch {
                _uiEffect.send(LoginUiEffect.NavigateToForgotPassword)
            }
        }

        private fun updateFormValidity() {
            val currentState = _uiState.value
            val isValid = currentState.areAllFieldsFilled()
            _uiState.value = currentState.copy(isFormValid = isValid)
        }

        /**
         * Performs the login operation
         */
        private fun performLogin() {
            val currentState = _uiState.value

            if (!currentState.isFormValid) {
                viewModelScope.launch {
                    _uiEffect.sendErrorSnackbar("Please fill in all fields")
                }
                return
            }

            val loginData =
                LoginData(
                    password = currentState.password,
                    usernameOrEmail = currentState.email.trim(),
                )

            viewModelScope.launch {
                try {
                    _uiEffect.sendInfoSnackbar("Signing you in...")

                    authRepository.login(loginData).collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = isLoading,
                                        loginError = null,
                                    )
                            },
                            onSuccess = { jwtData ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        loginSuccess = true,
                                        jwtData = jwtData,
                                        loginError = null,
                                    )

                                _uiEffect.send(LoginUiEffect.NavigateToHome)
                                _uiEffect.sendSuccessSnackbar(
                                    message = "Welcome back! ${jwtData?.userInfo?.username}. Login Success",
                                    actionLabel = "OK",
                                )
                            },
                            onError = { message, errorType, httpCode ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        loginError = message,
                                        loginSuccess = false,
                                    )

                                _uiEffect.sendErrorSnackbar(
                                    message = message ?: "Login failed. Please try again.",
                                    actionLabel = "Retry",
                                    onActionClick = { performLogin() },
                                )
                            },
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            loginError = "An unexpected error occurred: ${e.localizedMessage}",
                            loginSuccess = false,
                        )

                    viewModelScope.launch {
                        _uiEffect.sendErrorSnackbar(
                            message = "An unexpected error occurred",
                            actionLabel = "Try Again",
                            onActionClick = { performLogin() },
                        )
                    }
                }
            }
        }

        fun resetLoginState() {
            _uiState.value =
                _uiState.value.copy(
                    loginSuccess = false,
                    loginError = null,
                    jwtData = null,
                    isLoading = false,
                )
        }

        /**
         * Clears all form data
         */
        fun clearForm() {
            _uiState.value = LoginUiState()
        }
    }
