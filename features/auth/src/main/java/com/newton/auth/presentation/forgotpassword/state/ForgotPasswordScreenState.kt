package com.newton.auth.presentation.forgotpassword.state

import com.newton.auth.presentation.forgotpassword.view.components.ForgotPasswordFormData
import com.newton.auth.presentation.forgotpassword.view.components.ForgotPasswordFormErrors


data class ForgotPasswordScreenState(
    val formData: ForgotPasswordFormData = ForgotPasswordFormData(),
    val formErrors: ForgotPasswordFormErrors = ForgotPasswordFormErrors(),
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
    val resetEmailSent: Boolean = false,
    val errorMessage: String? = null,
)