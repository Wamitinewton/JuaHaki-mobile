package com.newton.auth.presentation.login.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.CustomTextButton
import com.newton.commonui.components.CustomTextField
import com.newton.commonui.components.PasswordTextField
import com.newton.core.enums.TextFieldSize

/**
 * Data class to hold login form data
 */
data class LoginFormData(
    val emailOrUsername: String = "",
    val password: String = "",
)

data class LoginFormErrors(
    val emailOrUsername: String? = null,
    val password: String? = null,
)

@Composable
fun LoginForm(
    formData: LoginFormData,
    onFormDataChange: (LoginFormData) -> Unit,
    errors: LoginFormErrors = LoginFormErrors(),
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Sign In to Your Account",
            style =
                MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            modifier = Modifier.padding(bottom = 8.dp),
        )

        CustomTextField(
            value = formData.emailOrUsername,
            onValueChange = { emailOrUsername ->
                onFormDataChange(formData.copy(emailOrUsername = emailOrUsername))
            },
            label = "Email or Username",
            placeholder = "Enter your email or username",
            leadingIcon = Icons.Default.AlternateEmail,
            isError = errors.emailOrUsername != null,
            errorMessage = errors.emailOrUsername,
            enabled = enabled,
            size = TextFieldSize.Medium,
            modifier = Modifier.fillMaxWidth(),
        )

        PasswordTextField(
            value = formData.password,
            onValueChange = { password ->
                onFormDataChange(formData.copy(password = password))
            },
            label = "Password",
            placeholder = "Enter your password",
            leadingIcon = Icons.Default.Lock,
            isError = errors.password != null,
            errorMessage = errors.password,
            enabled = enabled,
            size = TextFieldSize.Medium,
            visibilityIcon = Icons.Default.Visibility,
            visibilityOffIcon = Icons.Default.VisibilityOff,
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = true,
            onTogglePasswordVisibility = {}
        )

        CustomTextButton(
            onClick = onForgotPasswordClick,
            enabled = enabled,
            text = "Forgot Password?",
            textStyle =
                MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color =
                        if (enabled) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        },
                ),
            modifier = Modifier.align(Alignment.End),
        )
    }
}
