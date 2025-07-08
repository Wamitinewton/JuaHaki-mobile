package com.newton.auth.presentation.login.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.CustomTextButton
import com.newton.commonui.components.CustomTextField
import com.newton.commonui.components.PasswordTextField
import com.newton.core.enums.LoginMode
import com.newton.core.enums.TextFieldSize

/**
 * Data class to hold login form data
 */
@Composable
fun LoginForm(
    email: String,
    password: String,
    isPasswordVisible: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loginError: String? = null,
    onClearError: () -> Unit = {},
) {
    var loginMode by remember { mutableStateOf(LoginMode.EMAIL) }

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

        LoginModeSelector(
            selectedMode = loginMode,
            onModeChange = {
                loginMode = it
                onClearError()
            },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (loginMode) {
            LoginMode.EMAIL -> {
                CustomTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = "Email Address",
                    placeholder = "Enter your email address",
                    leadingIcon = Icons.Default.AlternateEmail,
                    isError = loginError != null,
                    errorMessage = if (loginError != null && loginMode == LoginMode.EMAIL) loginError else null,
                    enabled = enabled,
                    size = TextFieldSize.Medium,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            LoginMode.USERNAME -> {
                CustomTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = "Username",
                    placeholder = "Enter your username",
                    leadingIcon = Icons.Default.Person,
                    isError = loginError != null,
                    errorMessage = if (loginError != null && loginMode == LoginMode.USERNAME) loginError else null,
                    enabled = enabled,
                    size = TextFieldSize.Medium,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        // Password Field
        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Password",
            placeholder = "Enter your password",
            leadingIcon = Icons.Default.Lock,
            isError = loginError != null,
            errorMessage = loginError,
            enabled = enabled,
            size = TextFieldSize.Medium,
            visibilityIcon = Icons.Default.Visibility,
            visibilityOffIcon = Icons.Default.VisibilityOff,
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
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
