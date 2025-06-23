package com.newton.auth.presentation.forgotpassword.state

import com.newton.auth.presentation.forgotpassword.view.components.ResetPasswordFormData
import com.newton.auth.presentation.forgotpassword.view.components.ResetPasswordFormErrors

private fun isPasswordValid(formData: ResetPasswordFormData): Boolean {
    val password = formData.newPassword
    val confirmPassword = formData.confirmPassword

    val hasValidLength = password.length >= 8
    val hasUppercase = password.any { it.isUpperCase() }
    val hasLowercase = password.any { it.isLowerCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasSpecialChar = password.any { !it.isLetterOrDigit() }
    val passwordsMatch = password == confirmPassword && password.isNotBlank()

    return hasValidLength && hasUppercase && hasLowercase && hasDigit && hasSpecialChar && passwordsMatch
}


/**
 * State class for the Reset Password screen
 */
data class ResetPasswordScreenState(
    val formData: ResetPasswordFormData = ResetPasswordFormData(),
    val formErrors: ResetPasswordFormErrors = ResetPasswordFormErrors(),
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
) {

    fun validateForm(): ResetPasswordScreenState {
        val errors = ResetPasswordFormErrors()
        val isValid = isPasswordValid(formData)

        return copy(
            formErrors = errors,
            isFormValid = isValid,
        )
    }


    fun clearErrors(): ResetPasswordScreenState {
        return copy(formErrors = ResetPasswordFormErrors())
    }

    fun updateFormData(newFormData: ResetPasswordFormData): ResetPasswordScreenState {
        return copy(
            formData = newFormData,
            isFormValid = isPasswordValid(newFormData),
        ).clearErrors()
    }
}