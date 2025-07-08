package com.newton.auth.presentation.signup.state

import com.newton.core.utils.ValidationError
import com.newton.domain.models.auth.UserInfo

data class SignupUiState(
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val firstNameError: ValidationError? = null,
    val lastNameError: ValidationError? = null,
    val usernameError: ValidationError? = null,
    val phoneNumberError: ValidationError? = null,
    val emailError: ValidationError? = null,
    val passwordError: ValidationError? = null,
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isFormValid: Boolean = false,
    val signupSuccess: Boolean = false,
    val signupError: String? = null,
    val createdUser: UserInfo? = null,
) {
    fun hasAnyError(): Boolean =
        firstNameError != null ||
                lastNameError != null ||
                usernameError != null ||
                phoneNumberError != null ||
                emailError != null ||
                passwordError != null

    fun areAllFieldsFilled(): Boolean =
        firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                username.isNotBlank() &&
                phoneNumber.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank()
}
