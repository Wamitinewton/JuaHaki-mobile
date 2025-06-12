package com.newton.auth.presentation.login.state

import com.newton.auth.presentation.login.view.components.LoginFormData
import com.newton.auth.presentation.login.view.components.LoginFormErrors


data class LoginScreenState(
    val formData: LoginFormData = LoginFormData(),
    val formErrors: LoginFormErrors = LoginFormErrors(),
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
)