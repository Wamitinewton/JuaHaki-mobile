package com.newton.auth.presentation.forgotpassword.event

sealed class InitiatePasswordResetUiEvent {
    data class OnEmailChanged(
        val email: String,
    ) : InitiatePasswordResetUiEvent()

    object OnInitiatePasswordResetClicked : InitiatePasswordResetUiEvent()

    object OnClearError : InitiatePasswordResetUiEvent()

    object OnNavigateBack : InitiatePasswordResetUiEvent()

    object OnValidateEmail : InitiatePasswordResetUiEvent()

    object OnValidateAllFields : InitiatePasswordResetUiEvent()
}
