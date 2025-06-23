package com.newton.auth.presentation.forgotpassword.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.PasswordTextField
import com.newton.core.enums.TextFieldSize

/**
 * Data class to hold reset password form data
 */
data class ResetPasswordFormData(
    val newPassword: String = "",
    val confirmPassword: String = "",
)

data class ResetPasswordFormErrors(
    val newPassword: String? = null,
    val confirmPassword: String? = null,
)

@Composable
fun ResetPasswordForm(
    formData: ResetPasswordFormData,
    onFormDataChange: (ResetPasswordFormData) -> Unit,
    errors: ResetPasswordFormErrors = ResetPasswordFormErrors(),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val passwordValidation by remember(formData.newPassword) {
        derivedStateOf {
            PasswordValidation(
                hasValidLength = formData.newPassword.length >= 8,
                hasUppercase = formData.newPassword.any { it.isUpperCase() },
                hasLowercase = formData.newPassword.any { it.isLowerCase() },
                hasDigit = formData.newPassword.any { it.isDigit() },
                hasSpecialChar = formData.newPassword.any { !it.isLetterOrDigit() },
            )
        }
    }

    val passwordsMatch by remember(formData.newPassword, formData.confirmPassword) {
        derivedStateOf {
            formData.newPassword == formData.confirmPassword &&
                formData.newPassword.isNotBlank() &&
                formData.confirmPassword.isNotBlank()
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        PasswordTextField(
            value = formData.newPassword,
            onValueChange = { newPassword ->
                onFormDataChange(formData.copy(newPassword = newPassword))
            },
            label = "New Password",
            placeholder = "Enter your new password",
            leadingIcon = Icons.Default.Lock,
            isError = errors.newPassword != null,
            errorMessage = errors.newPassword,
            enabled = enabled,
            size = TextFieldSize.Medium,
            visibilityIcon = Icons.Default.Visibility,
            visibilityOffIcon = Icons.Default.VisibilityOff,
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = true,
            onTogglePasswordVisibility = {}
        )

        if (formData.newPassword.isNotBlank()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "Password Strength:",
                    style =
                        MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        ),
                )

                PasswordValidationIndicator(
                    text = "At least 8 characters",
                    isValid = passwordValidation.hasValidLength,
                )
                PasswordValidationIndicator(
                    text = "Uppercase letter (A-Z)",
                    isValid = passwordValidation.hasUppercase,
                )
                PasswordValidationIndicator(
                    text = "Lowercase letter (a-z)",
                    isValid = passwordValidation.hasLowercase,
                )
                PasswordValidationIndicator(
                    text = "Number (0-9)",
                    isValid = passwordValidation.hasDigit,
                )
                PasswordValidationIndicator(
                    text = "Special character (!@#$%^&*)",
                    isValid = passwordValidation.hasSpecialChar,
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        PasswordTextField(
            value = formData.confirmPassword,
            onValueChange = { confirmPassword ->
                onFormDataChange(formData.copy(confirmPassword = confirmPassword))
            },
            label = "Confirm New Password",
            placeholder = "Re-enter your new password",
            leadingIcon = Icons.Default.Lock,
            isError = errors.confirmPassword != null || (formData.confirmPassword.isNotBlank() && !passwordsMatch),
            errorMessage =
                errors.confirmPassword ?: if (formData.confirmPassword.isNotBlank() && !passwordsMatch) "Passwords do not match" else null,
            enabled = enabled,
            size = TextFieldSize.Medium,
            visibilityIcon = Icons.Default.Visibility,
            visibilityOffIcon = Icons.Default.VisibilityOff,
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = true,
            onTogglePasswordVisibility = {}
        )

        if (formData.confirmPassword.isNotBlank()) {
            PasswordValidationIndicator(
                text = "Passwords match",
                isValid = passwordsMatch,
            )
        }
    }
}

@Composable
private fun PasswordValidationIndicator(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint =
                if (isValid) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                },
            modifier = Modifier.size(16.dp),
        )

        Text(
            text = text,
            style =
                MaterialTheme.typography.bodySmall.copy(
                    color =
                        if (isValid) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        },
                ),
        )
    }
}

private data class PasswordValidation(
    val hasValidLength: Boolean = false,
    val hasUppercase: Boolean = false,
    val hasLowercase: Boolean = false,
    val hasDigit: Boolean = false,
    val hasSpecialChar: Boolean = false,
)
