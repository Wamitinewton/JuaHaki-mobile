package com.newton.auth.presentation.forgotpassword.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.CustomTextField
import com.newton.core.enums.TextFieldSize

/**
 * Data class to hold forgot password form data
 */
data class ForgotPasswordFormData(
    val email: String = "",
)

/**
 * Data class to hold forgot password form errors
 */
data class ForgotPasswordFormErrors(
    val email: String? = null,
)

@Composable
fun ForgotPasswordForm(
    formData: ForgotPasswordFormData,
    onFormDataChange: (ForgotPasswordFormData) -> Unit,
    errors: ForgotPasswordFormErrors = ForgotPasswordFormErrors(),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Enter Your Email Address",
            style =
                MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            modifier = Modifier.padding(bottom = 8.dp),
        )

        CustomTextField(
            value = formData.email,
            onValueChange = { email ->
                onFormDataChange(formData.copy(email = email))
            },
            label = "Email Address",
            placeholder = "Enter your email address",
            leadingIcon = Icons.Default.AlternateEmail,
            isError = errors.email != null,
            errorMessage = errors.email,
            enabled = enabled,
            size = TextFieldSize.Medium,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = "We'll send a password reset link to this email address if it's associated with an account.",
            style =
                MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight * 1.3,
                ),
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
