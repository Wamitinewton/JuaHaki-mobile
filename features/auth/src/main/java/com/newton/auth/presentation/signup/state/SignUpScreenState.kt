package com.newton.auth.presentation.signup.state

import com.newton.auth.presentation.signup.view.components.SignUpFormData
import com.newton.auth.presentation.signup.view.components.SignUpFormErrors

data class SignUpScreenState(
    val isLoading: Boolean = false,
    val formData: SignUpFormData = SignUpFormData(),
    val formErrors: SignUpFormErrors = SignUpFormErrors(),
    val isFormValid: Boolean = false,
    val showPrivacyDialog: Boolean = false,
)
