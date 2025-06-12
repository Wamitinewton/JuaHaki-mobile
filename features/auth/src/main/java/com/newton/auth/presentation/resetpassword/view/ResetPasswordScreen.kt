package com.newton.auth.presentation.resetpassword.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.auth.presentation.components.AlreadyOrDontHaveAccountSection
import com.newton.auth.presentation.resetpassword.state.ResetPasswordScreenState
import com.newton.auth.presentation.resetpassword.view.components.ResetPasswordForm
import com.newton.auth.presentation.resetpassword.view.components.ResetPasswordFormData
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.theme.backgroundGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onResetPassword: (ResetPasswordFormData) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    email: String? = null,
) {
    var screenState by remember {
        mutableStateOf(ResetPasswordScreenState(isLoading = isLoading))
    }

    screenState = screenState.copy(isLoading = isLoading)

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

                // Header Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Create New Password",
                        style =
                            MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Your new password must be different from previously used passwords and meet our security requirements.",
                        style =
                            MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2,
                            ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )

                    email?.let { userEmail ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "For: $userEmail",
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

                // Password Form Card
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
                    ) {
                        Text(
                            text = "Password Requirements",
                            style =
                                MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                ),
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "• At least 8 characters long\n• Contains uppercase and lowercase letters\n• Includes at least one number\n• Has at least one special character",
                            style =
                                MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                ),
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        ResetPasswordForm(
                            formData = screenState.formData,
                            onFormDataChange = { newFormData ->
                                screenState =
                                    screenState.copy(
                                        formData = newFormData,
                                        isFormValid = isPasswordValid(newFormData),
                                    )
                            },
                            errors = screenState.formErrors,
                            enabled = !screenState.isLoading,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        PrimaryButton(
                            text = if (screenState.isLoading) "Updating Password..." else "Update Password",
                            onClick = {
                                if (screenState.isFormValid && !screenState.isLoading) {
                                    onResetPassword(screenState.formData)
                                }
                            },
                            enabled = screenState.isFormValid && !screenState.isLoading,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        if (screenState.isLoading) {
                            Spacer(modifier = Modifier.height(16.dp))
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
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                AlreadyOrDontHaveAccountSection(
                    titleText = "Remember your password?",
                    buttonText = "Back to Sign In",
                    onNavigate = onNavigateToLogin,
                    enabled = !screenState.isLoading,
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
