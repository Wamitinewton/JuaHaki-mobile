package com.newton.auth.presentation.signup.event

sealed class SignupUiEvent {
    data class OnFirstNameChanged(
        val firstName: String,
    ) : SignupUiEvent()

    data class OnLastNameChanged(
        val lastName: String,
    ) : SignupUiEvent()

    data class OnUsernameChanged(
        val username: String,
    ) : SignupUiEvent()

    data class OnPhoneNumberChanged(
        val phoneNumber: String,
    ) : SignupUiEvent()

    data class OnEmailChanged(
        val email: String,
    ) : SignupUiEvent()

    data class OnPasswordChanged(
        val password: String,
    ) : SignupUiEvent()

    object OnTogglePasswordVisibility : SignupUiEvent()

    object OnSignupClicked : SignupUiEvent()

    object OnClearError : SignupUiEvent()

    object OnNavigateBack : SignupUiEvent()

    object OnValidateFirstName : SignupUiEvent()

    object OnValidateLastName : SignupUiEvent()

    object OnValidateUsername : SignupUiEvent()

    object OnValidatePhoneNumber : SignupUiEvent()

    object OnValidateEmail : SignupUiEvent()

    object OnValidatePassword : SignupUiEvent()

    object OnValidateAllFields : SignupUiEvent()
}
