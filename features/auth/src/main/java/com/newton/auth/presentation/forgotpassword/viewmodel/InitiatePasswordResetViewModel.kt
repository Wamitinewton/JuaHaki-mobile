package com.newton.auth.presentation.forgotpassword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.forgotpassword.event.InitiatePasswordResetUiEffect
import com.newton.auth.presentation.forgotpassword.event.InitiatePasswordResetUiEvent
import com.newton.auth.presentation.forgotpassword.state.InitiatePasswordResetUiState
import com.newton.core.extensions.sendErrorSnackbar
import com.newton.core.extensions.sendInfoSnackbar
import com.newton.core.extensions.sendSuccessSnackbar
import com.newton.core.extensions.sendWarningSnackbar
import com.newton.core.utils.InputValidator
import com.newton.core.utils.ValidationResult
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
class InitiatePasswordResetViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        // UI State
        private val _uiState = MutableStateFlow(InitiatePasswordResetUiState())
        val uiState: StateFlow<InitiatePasswordResetUiState> = _uiState.asStateFlow()

        // One-time events
        private val _uiEffect = Channel<InitiatePasswordResetUiEffect>()
        val uiEffect = _uiEffect.receiveAsFlow()

        /**
         * Handles all UI events
         */
        fun onEvent(event: InitiatePasswordResetUiEvent) {
            when (event) {
                is InitiatePasswordResetUiEvent.OnEmailChanged -> updateEmail(event.email)

                // UI interactions
                InitiatePasswordResetUiEvent.OnInitiatePasswordResetClicked -> performInitiatePasswordReset()
                InitiatePasswordResetUiEvent.OnClearError -> clearError()
                InitiatePasswordResetUiEvent.OnNavigateBack -> navigateBack()

                // Field validations
                InitiatePasswordResetUiEvent.OnValidateEmail -> validateEmail()
                InitiatePasswordResetUiEvent.OnValidateAllFields -> validateAllFields()
            }
        }

        // Input field update methods
        private fun updateEmail(email: String) {
            _uiState.value =
                _uiState.value.copy(
                    email = email,
                    emailError = null,
                )
            updateFormValidity()
        }

        private fun clearError() {
            _uiState.value =
                _uiState.value.copy(
                    initiateError = null,
                )
        }

        private fun navigateBack() {
            viewModelScope.launch {
                _uiEffect.send(InitiatePasswordResetUiEffect.NavigateToLogin)
            }
        }

        private fun validateEmail() {
            val result = InputValidator.validateEmail(_uiState.value.email)
            _uiState.value =
                _uiState.value.copy(
                    emailError = if (result is ValidationResult.Invalid) result.error else null,
                )
            updateFormValidity()
        }

        private fun validateAllFields() {
            validateEmail()
        }

        private fun updateFormValidity() {
            val currentState = _uiState.value
            val isValid = currentState.areAllFieldsFilled() && !currentState.hasAnyError()
            _uiState.value = currentState.copy(isFormValid = isValid)
        }

        /**
         * Performs the initiate password reset operation
         */
        private fun performInitiatePasswordReset() {
            validateAllFields()

            val currentState = _uiState.value

            if (!currentState.isFormValid || currentState.hasAnyError()) {
                viewModelScope.launch {
                    _uiEffect.sendWarningSnackbar("Please fix the errors before proceeding")
                }
                return
            }

            val email = currentState.email.trim()

            viewModelScope.launch {
                try {
                    _uiEffect.sendInfoSnackbar("Sending reset instructions...")

                    authRepository.initiatePasswordReset(email).collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = isLoading,
                                        initiateError = null,
                                    )
                            },
                            onSuccess = {
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        initiateSuccess = true,
                                        initiateError = null,
                                    )

                                _uiEffect.send(
                                    InitiatePasswordResetUiEffect.NavigateToResetPassword(
                                        email,
                                    ),
                                )
                                _uiEffect.sendSuccessSnackbar(
                                    message = "Reset instructions sent! Please check your email.",
                                    actionLabel = "OK",
                                )
                            },
                            onError = { message, errorType, httpCode ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        initiateError = message,
                                        initiateSuccess = false,
                                    )

                                _uiEffect.sendErrorSnackbar(
                                    message = message ?: "Failed to send reset instructions",
                                    actionLabel = "Retry",
                                    onActionClick = { performInitiatePasswordReset() },
                                )
                            },
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            initiateError = "An unexpected error occurred: ${e.localizedMessage}",
                            initiateSuccess = false,
                        )

                    viewModelScope.launch {
                        _uiEffect.sendErrorSnackbar(
                            message = "An unexpected error occurred",
                            actionLabel = "Try Again",
                            onActionClick = { performInitiatePasswordReset() },
                        )
                    }
                }
            }
        }

        /**
         * Resets the initiate password reset state
         */
        fun resetInitiateState() {
            _uiState.value =
                _uiState.value.copy(
                    initiateSuccess = false,
                    initiateError = null,
                    isLoading = false,
                )
        }

        /**
         * Clears all form data
         */
        fun clearForm() {
            _uiState.value = InitiatePasswordResetUiState()
        }
    }
