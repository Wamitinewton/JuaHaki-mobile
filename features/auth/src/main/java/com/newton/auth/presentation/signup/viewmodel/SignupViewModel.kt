package com.newton.auth.presentation.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.signup.event.SignupUiEffect
import com.newton.auth.presentation.signup.event.SignupUiEvent
import com.newton.auth.presentation.signup.state.SignupUiState
import com.newton.core.extensions.sendErrorSnackbar
import com.newton.core.extensions.sendInfoSnackbar
import com.newton.core.extensions.sendSuccessSnackbar
import com.newton.core.extensions.sendWarningSnackbar
import com.newton.core.utils.InputValidator
import com.newton.core.utils.ValidationResult
import com.newton.domain.models.auth.SignupData
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
class SignupViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        // UI State
        private val _uiState = MutableStateFlow(SignupUiState())
        val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

        // One-time events
        private val _uiEffect = Channel<SignupUiEffect>()
        val uiEffect = _uiEffect.receiveAsFlow()

        /**
         * Handles all UI events
         */
        fun onEvent(event: SignupUiEvent) {
            when (event) {
                // Input field changes
                is SignupUiEvent.OnFirstNameChanged -> updateFirstName(event.firstName)
                is SignupUiEvent.OnLastNameChanged -> updateLastName(event.lastName)
                is SignupUiEvent.OnUsernameChanged -> updateUsername(event.username)
                is SignupUiEvent.OnPhoneNumberChanged -> updatePhoneNumber(event.phoneNumber)
                is SignupUiEvent.OnEmailChanged -> updateEmail(event.email)
                is SignupUiEvent.OnPasswordChanged -> updatePassword(event.password)

                // UI interactions
                SignupUiEvent.OnTogglePasswordVisibility -> togglePasswordVisibility()
                SignupUiEvent.OnSignupClicked -> performSignup()
                SignupUiEvent.OnClearError -> clearError()
                SignupUiEvent.OnNavigateBack -> navigateBack()

                // Field validations
                SignupUiEvent.OnValidateFirstName -> validateFirstName()
                SignupUiEvent.OnValidateLastName -> validateLastName()
                SignupUiEvent.OnValidateUsername -> validateUsername()
                SignupUiEvent.OnValidatePhoneNumber -> validatePhoneNumber()
                SignupUiEvent.OnValidateEmail -> validateEmail()
                SignupUiEvent.OnValidatePassword -> validatePassword()
                SignupUiEvent.OnValidateAllFields -> validateAllFields()
            }
        }

        // Input field update methods
        private fun updateFirstName(firstName: String) {
            _uiState.value =
                _uiState.value.copy(
                    firstName = firstName,
                    firstNameError = null,
                )
            updateFormValidity()
        }

        private fun updateLastName(lastName: String) {
            _uiState.value =
                _uiState.value.copy(
                    lastName = lastName,
                    lastNameError = null,
                )
            updateFormValidity()
        }

        private fun updateUsername(username: String) {
            _uiState.value =
                _uiState.value.copy(
                    username = username,
                    usernameError = null,
                )
            updateFormValidity()
        }

        private fun updatePhoneNumber(phoneNumber: String) {
            _uiState.value =
                _uiState.value.copy(
                    phoneNumber = phoneNumber,
                    phoneNumberError = null,
                )
            updateFormValidity()
        }

        private fun updateEmail(email: String) {
            _uiState.value =
                _uiState.value.copy(
                    email = email,
                    emailError = null,
                )
            updateFormValidity()
        }

        private fun updatePassword(password: String) {
            _uiState.value =
                _uiState.value.copy(
                    password = password,
                    passwordError = null,
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
                    signupError = null,
                )
        }

        private fun navigateBack() {
            viewModelScope.launch {
                _uiEffect.send(SignupUiEffect.NavigateToLogin)
            }
        }

        private fun validateFirstName() {
            val result = InputValidator.validateName(_uiState.value.firstName)
            _uiState.value =
                _uiState.value.copy(
                    firstNameError = if (result is ValidationResult.Invalid) result.error else null,
                )
            updateFormValidity()
        }

        private fun validateLastName() {
            val result = InputValidator.validateName(_uiState.value.lastName)
            _uiState.value =
                _uiState.value.copy(
                    lastNameError = if (result is ValidationResult.Invalid) result.error else null,
                )
            updateFormValidity()
        }

        private fun validateUsername() {
            val result = InputValidator.validateUsername(_uiState.value.username)
            _uiState.value =
                _uiState.value.copy(
                    usernameError = if (result is ValidationResult.Invalid) result.error else null,
                )
            updateFormValidity()
        }

        private fun validatePhoneNumber() {
            val result = InputValidator.validatePhoneNumber(_uiState.value.phoneNumber)
            _uiState.value =
                _uiState.value.copy(
                    phoneNumberError = if (result is ValidationResult.Invalid) result.error else null,
                )
            updateFormValidity()
        }

        private fun validateEmail() {
            val result = InputValidator.validateEmail(_uiState.value.email)
            _uiState.value =
                _uiState.value.copy(
                    emailError = if (result is ValidationResult.Invalid) result.error else null,
                )
            updateFormValidity()
        }

        private fun validatePassword() {
            val result = InputValidator.validatePassword(_uiState.value.password)
            _uiState.value =
                _uiState.value.copy(
                    passwordError = if (result is ValidationResult.Invalid) result.error else null,
                )
            updateFormValidity()
        }

        private fun validateAllFields() {
            val currentState = _uiState.value
            val validationErrors =
                InputValidator.validateSignupData(
                    firstName = currentState.firstName,
                    lastName = currentState.lastName,
                    username = currentState.username,
                    phoneNumber = currentState.phoneNumber,
                    email = currentState.email,
                    password = currentState.password,
                )

            _uiState.value =
                currentState.copy(
                    firstNameError = validationErrors["firstName"],
                    lastNameError = validationErrors["lastName"],
                    usernameError = validationErrors["username"],
                    phoneNumberError = validationErrors["phoneNumber"],
                    emailError = validationErrors["email"],
                    passwordError = validationErrors["password"],
                )
            updateFormValidity()
        }

        private fun updateFormValidity() {
            val currentState = _uiState.value
            val isValid = currentState.areAllFieldsFilled() && !currentState.hasAnyError()
            _uiState.value = currentState.copy(isFormValid = isValid)
        }

        /**
         * Performs the signup operation
         */
        private fun performSignup() {
            validateAllFields()

            val currentState = _uiState.value

            if (!currentState.isFormValid || currentState.hasAnyError()) {
                viewModelScope.launch {
                    _uiEffect.sendWarningSnackbar("Please fix the errors before proceeding")
                }
                return
            }

            val signupData =
                SignupData(
                    firstName = currentState.firstName.trim(),
                    lastName = currentState.lastName.trim(),
                    username = currentState.username.trim(),
                    phoneNumber = currentState.phoneNumber.trim(),
                    email = currentState.email.trim(),
                    password = currentState.password,
                )

            viewModelScope.launch {
                try {
                    _uiEffect.sendInfoSnackbar("Creating your account....")
                    authRepository.createUser(signupData).collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = isLoading,
                                        signupError = null,
                                    )
                            },
                            onSuccess = { userInfo ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        signupSuccess = true,
                                        createdUser = userInfo,
                                        signupError = null,
                                    )

                                _uiEffect.send(SignupUiEffect.NavigateToEmailVerification)
                                _uiEffect.sendSuccessSnackbar(
                                    message = "Account created successfully! Please verify your email.",
                                    actionLabel = "OK"
                                )                            },
                            onError = { message, errorType, httpCode ->

                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        signupError = message,
                                        signupSuccess = false,
                                    )

                                _uiEffect.sendErrorSnackbar(
                                    message = message ?: "Unknown error occurred",
                                    actionLabel = "Retry",
                                    onActionClick = { performSignup() }
                                )                            },
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            signupError = "An unexpected error occurred: ${e.localizedMessage}",
                            signupSuccess = false,
                        )

                    viewModelScope.launch {
                        _uiEffect.sendErrorSnackbar(
                            message = "An unexpected error occurred",
                            actionLabel = "Try Again",
                            onActionClick = { performSignup() }
                        )                    }
                }
            }
        }

        /**
         * Resets the signup state (useful for retry scenarios)
         */
        fun resetSignupState() {
            _uiState.value =
                _uiState.value.copy(
                    signupSuccess = false,
                    signupError = null,
                    createdUser = null,
                    isLoading = false,
                )
        }

        /**
         * Clears all form data
         */
        fun clearForm() {
            _uiState.value = SignupUiState()
        }
    }
