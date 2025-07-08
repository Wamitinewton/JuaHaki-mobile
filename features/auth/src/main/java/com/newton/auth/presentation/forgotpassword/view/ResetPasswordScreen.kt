package com.newton.auth.presentation.forgotpassword.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.auth.presentation.components.AlreadyOrDontHaveAccountSection
import com.newton.auth.presentation.forgotpassword.event.ResetPasswordUiEvent
import com.newton.auth.presentation.forgotpassword.state.ResetPasswordUiState
import com.newton.commonui.components.OtpTextField
import com.newton.commonui.components.PasswordTextField
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.theme.backgroundGradient
import com.newton.core.enums.TextFieldSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    uiState: ResetPasswordUiState,
    onEvent: (ResetPasswordUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val passwordValidation by remember(uiState.newPassword) {
        derivedStateOf {
            PasswordValidationState(
                hasValidLength = uiState.newPassword.length >= 8,
                hasUppercase = uiState.newPassword.any { it.isUpperCase() },
                hasLowercase = uiState.newPassword.any { it.isLowerCase() },
                hasDigit = uiState.newPassword.any { it.isDigit() },
                hasSpecialChar = uiState.newPassword.any { !it.isLetterOrDigit() },
            )
        }
    }

    val passwordsMatch by remember(uiState.newPassword, uiState.confirmPassword) {
        derivedStateOf {
            uiState.newPassword == uiState.confirmPassword &&
                    uiState.newPassword.isNotBlank() &&
                    uiState.confirmPassword.isNotBlank()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Reset Password",
                        style =
                            MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                            ),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                    ),
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(backgroundGradient())
                    .padding(paddingValues),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                        .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Verify and Reset",
                        style =
                            MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Enter the verification code sent to your email and create a new secure password.",
                        style =
                            MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2,
                            ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )

                    if (uiState.email.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "For: ${uiState.email}",
                            style =
                                MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Medium,
                                ),
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = "Verification Code",
                                style =
                                    MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    ),
                            )

                            Text(
                                text = "Enter the 6-digit code sent to your email",
                                style =
                                    MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                    ),
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OtpTextField(
                                value = uiState.otp,
                                onValueChange = { otp ->
                                    onEvent(ResetPasswordUiEvent.OnOtpChanged(otp))
                                },
                                onOtpComplete = {
                                    onEvent(ResetPasswordUiEvent.OnValidateOtp)
                                },
                                otpLength = 6,
                                enabled = !uiState.isLoading,
                                isError = uiState.otpError != null,
                                modifier = Modifier.fillMaxWidth(),
                            )

                            uiState.otpError?.let { error ->
                                Text(
                                    text = error.message,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error,
                                )
                            }
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = "New Password",
                                style =
                                    MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    ),
                            )

                            PasswordTextField(
                                value = uiState.newPassword,
                                onValueChange = { password ->
                                    onEvent(ResetPasswordUiEvent.OnNewPasswordChanged(password))
                                },
                                label = "New Password",
                                placeholder = "Enter your new password",
                                leadingIcon = Icons.Default.Lock,
                                isError = uiState.newPasswordError != null,
                                errorMessage = uiState.newPasswordError?.message,
                                enabled = !uiState.isLoading,
                                size = TextFieldSize.Medium,
                                visibilityIcon = Icons.Default.Visibility,
                                visibilityOffIcon = Icons.Default.VisibilityOff,
                                modifier = Modifier.fillMaxWidth(),
                                isPasswordVisible = uiState.isNewPasswordVisible,
                                onTogglePasswordVisibility = {
                                    onEvent(ResetPasswordUiEvent.OnToggleNewPasswordVisibility)
                                },
                            )

                            if (uiState.newPassword.isNotBlank()) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                ) {
                                    Text(
                                        text = "Password Requirements:",
                                        style =
                                            MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.8f
                                                ),
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

                            Spacer(modifier = Modifier.height(8.dp))

                            PasswordTextField(
                                value = uiState.confirmPassword,
                                onValueChange = { password ->
                                    onEvent(ResetPasswordUiEvent.OnConfirmPasswordChanged(password))
                                },
                                label = "Confirm New Password",
                                placeholder = "Re-enter your new password",
                                leadingIcon = Icons.Default.Lock,
                                isError = uiState.confirmPasswordError != null,
                                errorMessage = uiState.confirmPasswordError?.message,
                                enabled = !uiState.isLoading,
                                size = TextFieldSize.Medium,
                                visibilityIcon = Icons.Default.Visibility,
                                visibilityOffIcon = Icons.Default.VisibilityOff,
                                modifier = Modifier.fillMaxWidth(),
                                isPasswordVisible = uiState.isConfirmPasswordVisible,
                                onTogglePasswordVisibility = {
                                    onEvent(ResetPasswordUiEvent.OnToggleConfirmPasswordVisibility)
                                },
                            )

                            if (uiState.confirmPassword.isNotBlank()) {
                                PasswordValidationIndicator(
                                    text = "Passwords match",
                                    isValid = passwordsMatch,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        PrimaryButton(
                            text = if (uiState.isLoading) "Resetting Password..." else "Reset Password",
                            onClick = {
                                onEvent(ResetPasswordUiEvent.OnResetPasswordClicked)
                            },
                            enabled = uiState.isFormValid && !uiState.isLoading,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        if (uiState.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(8.dp),
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }

                        uiState.resetError?.let { error ->
                            Text(
                                text = error,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                AlreadyOrDontHaveAccountSection(
                    titleText = "Remember your password?",
                    buttonText = "Back to Sign In",
                    onNavigate = {
                        onEvent(ResetPasswordUiEvent.OnNavigateBack)
                    },
                    enabled = !uiState.isLoading,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
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

private data class PasswordValidationState(
    val hasValidLength: Boolean = false,
    val hasUppercase: Boolean = false,
    val hasLowercase: Boolean = false,
    val hasDigit: Boolean = false,
    val hasSpecialChar: Boolean = false,
)
