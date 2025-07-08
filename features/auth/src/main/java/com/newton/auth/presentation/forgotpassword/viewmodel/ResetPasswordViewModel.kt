package com.newton.auth.presentation.forgotpassword.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.forgotpassword.event.ResetPasswordUiEffect
import com.newton.auth.presentation.forgotpassword.event.ResetPasswordUiEvent
import com.newton.auth.presentation.forgotpassword.state.ResetPasswordUiState
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
class ResetPasswordViewModel
@Inject
constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState: StateFlow<ResetPasswordUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<ResetPasswordUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    companion object {
        private const val EMAIL_KEY = "email"
    }

    init {
        val email = savedStateHandle.get<String>(EMAIL_KEY) ?: ""
        if (email.isNotEmpty()) {
            updateEmail(email)
        }
    }

    /**
     * Handles all UI events
     */
    fun onEvent(event: ResetPasswordUiEvent) {
        when (event) {
            is ResetPasswordUiEvent.OnEmailChanged -> updateEmail(event.email)
            is ResetPasswordUiEvent.OnOtpChanged -> updateOtp(event.otp)
            is ResetPasswordUiEvent.OnNewPasswordChanged -> updateNewPassword(event.newPassword)
            is ResetPasswordUiEvent.OnConfirmPasswordChanged -> updateConfirmPassword(event.confirmPassword)

            ResetPasswordUiEvent.OnToggleNewPasswordVisibility -> toggleNewPasswordVisibility()
            ResetPasswordUiEvent.OnToggleConfirmPasswordVisibility -> toggleConfirmPasswordVisibility()
            ResetPasswordUiEvent.OnResetPasswordClicked -> performResetPassword()
            ResetPasswordUiEvent.OnClearError -> clearError()
            ResetPasswordUiEvent.OnNavigateBack -> navigateBack()

            ResetPasswordUiEvent.OnValidateNewPassword -> validateNewPassword()
            ResetPasswordUiEvent.OnValidateAllFields -> validateAllFields()
            ResetPasswordUiEvent.OnValidateOtp -> validateOtp()
            ResetPasswordUiEvent.OnValidateConfirmPassword -> validateConfirmPassword()
        }
    }

    // Input field update methods
    private fun updateEmail(email: String) {
        _uiState.value =
            _uiState.value.copy(
                email = email.trim(),
                resetError = null,
            )
        updateFormValidity()
    }

    private fun updateOtp(otp: String) {
        _uiState.value =
            _uiState.value.copy(
                otp = otp,
                otpError = null,
            )
        if (otp.length == 6) validateOtp()
        updateFormValidity()
    }

    private fun updateNewPassword(newPassword: String) {
        _uiState.value =
            _uiState.value.copy(
                newPassword = newPassword,
                newPasswordError = null,
            )
        if (_uiState.value.confirmPassword.isNotBlank()) {
            validateConfirmPassword()
        }
        updateFormValidity()
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value =
            _uiState.value.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = null,
            )
        if (confirmPassword.isNotBlank()) validateConfirmPassword()
        updateFormValidity()
    }

    private fun toggleNewPasswordVisibility() {
        _uiState.value =
            _uiState.value.copy(
                isNewPasswordVisible = !_uiState.value.isNewPasswordVisible,
            )
    }

    private fun toggleConfirmPasswordVisibility() {
        _uiState.value =
            _uiState.value.copy(
                isConfirmPasswordVisible = !_uiState.value.isConfirmPasswordVisible,
            )
    }

    private fun clearError() {
        _uiState.value =
            _uiState.value.copy(
                resetError = null,
            )
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEffect.send(ResetPasswordUiEffect.NavigateToInitiatePasswordReset)
        }
    }

    private fun validateNewPassword() {
        val result = InputValidator.validatePassword(_uiState.value.newPassword)
        _uiState.value =
            _uiState.value.copy(
                newPasswordError = if (result is ValidationResult.Invalid) result.error else null,
            )
        updateFormValidity()
    }

    private fun validateAllFields() {
        validateOtp()
        validateNewPassword()
        validateConfirmPassword()
    }

    private fun updateFormValidity() {
        val currentState = _uiState.value
        val isValid = currentState.areAllFieldsFilled() && !currentState.hasAnyError()
        _uiState.value = currentState.copy(isFormValid = isValid)
    }

    private fun validateOtp() {
        val result = InputValidator.validateOtp(_uiState.value.otp)
        _uiState.value =
            _uiState.value.copy(
                otpError = if (result is ValidationResult.Invalid) result.error else null,
            )
        updateFormValidity()
    }

    private fun validateConfirmPassword() {
        val currentState = _uiState.value
        val result =
            InputValidator.validatePasswordMatch(
                password = currentState.newPassword,
                confirmPassword = currentState.confirmPassword,
            )

        _uiState.value =
            currentState.copy(
                confirmPasswordError = if (result is ValidationResult.Invalid) result.error else null,
            )
        updateFormValidity()
    }

    /**
     * Performs the reset password operation
     */
    private fun performResetPassword() {
        validateAllFields()

        val currentState = _uiState.value

        if (!currentState.isFormValid || currentState.hasAnyError()) {
            viewModelScope.launch {
                _uiEffect.sendWarningSnackbar("Please fix the errors before proceeding")
            }
            return
        }

        viewModelScope.launch {
            try {
                _uiEffect.sendInfoSnackbar("Resetting your password...")

                authRepository
                    .resetPassword(
                        email = currentState.email,
                        otp = currentState.otp.trim(),
                        newPassword = currentState.newPassword,
                    ).collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = isLoading,
                                        resetError = null,
                                    )
                            },
                            onSuccess = {
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        resetSuccess = true,
                                        resetError = null,
                                    )

                                _uiEffect.send(ResetPasswordUiEffect.NavigateToLogin)
                                _uiEffect.sendSuccessSnackbar(
                                    message = "Password reset successful! Please login with your new password.",
                                    actionLabel = "OK",
                                )
                            },
                            onError = { message, errorType, httpCode ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        resetError = message,
                                        resetSuccess = false,
                                    )

                                _uiEffect.sendErrorSnackbar(
                                    message = message ?: "Failed to reset password",
                                    actionLabel = "Retry",
                                    onActionClick = { performResetPassword() },
                                )
                            },
                        )
                    }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        resetError = "An unexpected error occurred: ${e.localizedMessage}",
                        resetSuccess = false,
                    )

                viewModelScope.launch {
                    _uiEffect.sendErrorSnackbar(
                        message = "An unexpected error occurred",
                        actionLabel = "Try Again",
                        onActionClick = { performResetPassword() },
                    )
                }
            }
        }
    }

    /**
     * Resets the password reset state
     */
    fun resetPasswordResetState() {
        _uiState.value =
            _uiState.value.copy(
                resetSuccess = false,
                resetError = null,
                isLoading = false,
            )
    }

    /**
     * Clears form data (excluding email)
     */
    fun clearForm() {
        val currentEmail = _uiState.value.email
        _uiState.value = ResetPasswordUiState(email = currentEmail)
    }
}
