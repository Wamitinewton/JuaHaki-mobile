package com.newton.auth.presentation.login.event

sealed class LoginUiEvent {
    data class OnEmailChanged(
        val email: String,
    ) : LoginUiEvent()

    data class OnPasswordChanged(
        val password: String,
    ) : LoginUiEvent()

    object OnTogglePasswordVisibility : LoginUiEvent()

    object OnLoginClicked : LoginUiEvent()

    object OnClearError : LoginUiEvent()

    object OnNavigateToSignup : LoginUiEvent()

    object OnNavigateToForgotPassword : LoginUiEvent()
}