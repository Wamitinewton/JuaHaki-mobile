package com.newton.auth.presentation.accountverification.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.accountverification.event.OtpUiEffect
import com.newton.auth.presentation.accountverification.event.OtpUiEvent
import com.newton.auth.presentation.accountverification.state.AccountVerificationUiState
import com.newton.core.extensions.sendErrorSnackbar
import com.newton.core.extensions.sendInfoSnackbar
import com.newton.core.extensions.sendSuccessSnackbar
import com.newton.domain.models.auth.VerifyOtpData
import com.newton.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountVerificationViewModel
@Inject
constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AccountVerificationUiState())
    val uiState: StateFlow<AccountVerificationUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<OtpUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private var resendCooldownJob: Job? = null

    companion object {
        private const val RESEND_COOLDOWN_SECONDS = 60
        private const val EMAIL_KEY = "email"
    }

    init {
        val email = savedStateHandle.get<String>(EMAIL_KEY) ?: ""
        if (email.isNotEmpty()) {
            updateEmail(email)
        }
    }

    fun startInitialCooldown() {
        if (_uiState.value.canResend) {
            startResendCooldown()
        }
    }

    /**
     * Handles all UI events
     */
    fun onEvent(event: OtpUiEvent) {
        when (event) {
            is OtpUiEvent.OnOtpChanged -> updateOtp(event.otp)
            is OtpUiEvent.OnEmailChanged -> updateEmail(event.email)

            OtpUiEvent.OnVerifyOtpClicked -> performOtpVerification()
            OtpUiEvent.OnResendOtpClicked -> performResendOtp()
            OtpUiEvent.OnClearError -> clearErrors()
            OtpUiEvent.OnNavigateBack -> navigateBack()
            OtpUiEvent.OnNavigateToLogin -> navigateToLogin()
        }
    }

    private fun updateOtp(otp: String) {
        val filteredOtp = otp.filter { it.isLetterOrDigit() }.take(6)

        _uiState.value =
            _uiState.value.copy(
                otp = filteredOtp,
                verificationError = null,
            )
        updateFormValidity()
    }

    private fun updateEmail(email: String) {
        _uiState.value =
            _uiState.value.copy(
                email = email.trim(),
                verificationError = null,
                resendError = null,
            )
        updateFormValidity()
    }

    private fun clearErrors() {
        _uiState.value =
            _uiState.value.copy(
                verificationError = null,
                resendError = null,
            )
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEffect.send(OtpUiEffect.NavigateBack)
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            _uiEffect.send(OtpUiEffect.NavigateToLogin)
        }
    }

    private fun updateFormValidity() {
        val currentState = _uiState.value
        val isValid =
            currentState.areAllFieldsFilled() &&
                    currentState.isOtpValid() &&
                    currentState.isEmailValid()
        _uiState.value = currentState.copy(isFormValid = isValid)
    }

    /**
     * Performs OTP verification
     */
    private fun performOtpVerification() {
        val currentState = _uiState.value

        if (!currentState.isFormValid) {
            viewModelScope.launch {
                when {
                    currentState.email.isBlank() -> {
                        _uiEffect.sendErrorSnackbar("Please enter your email address")
                    }

                    !currentState.isEmailValid() -> {
                        _uiEffect.sendErrorSnackbar("Please enter a valid email address")
                    }

                    currentState.otp.isBlank() -> {
                        _uiEffect.sendErrorSnackbar("Please enter verification code")
                    }

                    !currentState.isOtpValid() -> {
                        _uiEffect.sendErrorSnackbar("Please enter a valid 6-digit verification code")
                    }

                    else -> {
                        _uiEffect.sendErrorSnackbar("Please fill in all fields correctly")
                    }
                }
            }
            return
        }

        val verifyOtpData =
            VerifyOtpData(
                email = currentState.email,
                otp = currentState.otp,
            )

        viewModelScope.launch {
            try {
                _uiEffect.sendInfoSnackbar("Verifying your account...")

                authRepository.activateUserAccount(verifyOtpData).collect { resource ->
                    resource.handle(
                        onLoading = { isLoading ->
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = isLoading,
                                    verificationError = null,
                                )
                        },
                        onSuccess = {
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = false,
                                    verificationSuccess = true,
                                    verificationError = null,
                                )

                            _uiEffect.send(OtpUiEffect.NavigateToLogin)
                            _uiEffect.sendSuccessSnackbar(
                                message = "Account verified successfully! You can now sign in.",
                                actionLabel = "OK",
                            )
                        },
                        onError = { message, errorType, httpCode ->
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = false,
                                    verificationError = message,
                                    verificationSuccess = false,
                                )

                            _uiEffect.sendErrorSnackbar(
                                message = message ?: "Verification failed. Please try again.",
                                actionLabel = "Retry",
                                onActionClick = { performOtpVerification() },
                            )
                        },
                    )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        verificationError = "An unexpected error occurred: ${e.localizedMessage}",
                        verificationSuccess = false,
                    )

                viewModelScope.launch {
                    _uiEffect.sendErrorSnackbar(
                        message = "An unexpected error occurred",
                        actionLabel = "Try Again",
                        onActionClick = { performOtpVerification() },
                    )
                }
            }
        }
    }

    /**
     * Performs resend OTP operation
     */
    private fun performResendOtp() {
        val currentState = _uiState.value

        if (!currentState.canResend) {
            viewModelScope.launch {
                _uiEffect.sendInfoSnackbar("Please wait ${currentState.resendCooldownSeconds} seconds before requesting another code")
            }
            return
        }

        if (currentState.email.isBlank() || !currentState.isEmailValid()) {
            viewModelScope.launch {
                _uiEffect.sendErrorSnackbar("Please enter a valid email address")
            }
            return
        }

        viewModelScope.launch {
            try {
                _uiEffect.sendInfoSnackbar("Sending verification code...")

                authRepository.resendEmailVerificationOtp(currentState.email).collect { resource ->
                    resource.handle(
                        onLoading = { isLoading ->
                            _uiState.value =
                                _uiState.value.copy(
                                    isResendLoading = isLoading,
                                    resendError = null,
                                )
                        },
                        onSuccess = {
                            _uiState.value =
                                _uiState.value.copy(
                                    isResendLoading = false,
                                    resendSuccess = true,
                                    resendError = null,
                                )

                            startResendCooldown()

                            _uiEffect.sendSuccessSnackbar(
                                message = "Verification code sent to ${currentState.email}",
                                actionLabel = "OK",
                            )
                        },
                        onError = { message, errorType, httpCode ->
                            _uiState.value =
                                _uiState.value.copy(
                                    isResendLoading = false,
                                    resendError = message,
                                    resendSuccess = false,
                                )

                            _uiEffect.sendErrorSnackbar(
                                message = message
                                    ?: "Failed to send verification code. Please try again.",
                                actionLabel = "Retry",
                                onActionClick = { performResendOtp() },
                            )
                        },
                    )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        isResendLoading = false,
                        resendError = "An unexpected error occurred: ${e.localizedMessage}",
                        resendSuccess = false,
                    )

                viewModelScope.launch {
                    _uiEffect.sendErrorSnackbar(
                        message = "Failed to send verification code",
                        actionLabel = "Try Again",
                        onActionClick = { performResendOtp() },
                    )
                }
            }
        }
    }

    /**
     * Starts the resend cooldown timer
     */
    private fun startResendCooldown() {
        resendCooldownJob?.cancel()
        resendCooldownJob =
            viewModelScope.launch {
                _uiState.value =
                    _uiState.value.copy(
                        canResend = false,
                        resendCooldownSeconds = RESEND_COOLDOWN_SECONDS,
                    )

                repeat(RESEND_COOLDOWN_SECONDS) {
                    delay(1000)
                    val currentSeconds = _uiState.value.resendCooldownSeconds - 1
                    _uiState.value =
                        _uiState.value.copy(
                            resendCooldownSeconds = currentSeconds,
                        )
                }

                _uiState.value =
                    _uiState.value.copy(
                        canResend = true,
                        resendCooldownSeconds = 0,
                    )
            }
    }

    /**
     * Resets the verification state
     */
    fun resetVerificationState() {
        _uiState.value =
            _uiState.value.copy(
                verificationSuccess = false,
                verificationError = null,
                resendSuccess = false,
                resendError = null,
                isLoading = false,
                isResendLoading = false,
            )
    }

    /**
     * Clears the OTP field
     */
    fun clearOtp() {
        _uiState.value =
            _uiState.value.copy(
                otp = "",
                verificationError = null,
            )
        updateFormValidity()
    }

    /**
     * Clears all form data
     */
    fun clearForm() {
        resendCooldownJob?.cancel()
        _uiState.value = AccountVerificationUiState()
    }

    override fun onCleared() {
        super.onCleared()
        resendCooldownJob?.cancel()
    }
}
