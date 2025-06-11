package com.newton.auth.presentation.signup.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.CustomTextField
import com.newton.commonui.components.EmailTextField
import com.newton.commonui.components.PasswordTextField
import com.newton.commonui.components.PhoneTextField
import com.newton.core.enums.TextFieldSize

/**
 * Data class to hold sign up form data
 */
data class SignUpFormData(
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
)

data class SignUpFormErrors(
    val email: String? = null,
    val phone: String? = null,
    val password: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val username: String? = null,
)

@Composable
fun SignUpForm(
    formData: SignUpFormData,
    onFormDataChange: (SignUpFormData) -> Unit,
    errors: SignUpFormErrors = SignUpFormErrors(),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Required Information",
            style =
                MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            modifier = Modifier.padding(bottom = 8.dp),
        )

        EmailTextField(
            value = formData.email,
            onValueChange = { email ->
                onFormDataChange(formData.copy(email = email))
            },
            label = "Email Address *",
            placeholder = "Enter your email address",
            leadingIcon = Icons.Default.Email,
            isError = errors.email != null,
            errorMessage = errors.email,
            enabled = enabled,
            size = TextFieldSize.Medium,
            modifier = Modifier.fillMaxWidth(),
        )

        PhoneTextField(
            value = formData.phone,
            onValueChange = { phone ->
                onFormDataChange(formData.copy(phone = phone))
            },
            label = "Phone Number *",
            placeholder = "Enter your phone number",
            leadingIcon = Icons.Default.Phone,
            isError = errors.phone != null,
            errorMessage = errors.phone,
            enabled = enabled,
            size = TextFieldSize.Medium,
            modifier = Modifier.fillMaxWidth(),
        )

        PasswordTextField(
            value = formData.password,
            onValueChange = { password ->
                onFormDataChange(formData.copy(password = password))
            },
            label = "Password *",
            placeholder = "Create a strong password",
            leadingIcon = Icons.Default.Lock,
            isError = errors.password != null,
            errorMessage = errors.password,
            enabled = enabled,
            size = TextFieldSize.Medium,
            visibilityIcon = Icons.Default.Visibility,
            visibilityOffIcon = Icons.Default.VisibilityOff,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Optional Information",
            style =
                MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                ),
            modifier = Modifier.padding(bottom = 8.dp),
        )

        Text(
            text = "Help us personalize your experience (you can skip these)",
            style =
                MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                ),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CustomTextField(
                value = formData.firstName,
                onValueChange = { firstName ->
                    onFormDataChange(formData.copy(firstName = firstName))
                },
                label = "First Name",
                placeholder = "First name",
                leadingIcon = Icons.Default.Person,
                isError = errors.firstName != null,
                errorMessage = errors.firstName,
                enabled = enabled,
                size = TextFieldSize.Medium,
                modifier = Modifier.weight(1f),
            )

            CustomTextField(
                value = formData.lastName,
                onValueChange = { lastName ->
                    onFormDataChange(formData.copy(lastName = lastName))
                },
                label = "Last Name",
                placeholder = "Last name",
                isError = errors.lastName != null,
                errorMessage = errors.lastName,
                enabled = enabled,
                size = TextFieldSize.Medium,
                modifier = Modifier.weight(1f),
            )
        }

        CustomTextField(
            value = formData.username,
            onValueChange = { username ->
                onFormDataChange(formData.copy(username = username))
            },
            label = "Username",
            placeholder = "Choose a unique username",
            helperText = "This will be your unique identifier in the app",
            isError = errors.username != null,
            errorMessage = errors.username,
            enabled = enabled,
            size = TextFieldSize.Medium,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
