package com.newton.auth.presentation.forgotpassword.view

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.auth.presentation.components.AlreadyOrDontHaveAccountSection
import com.newton.auth.presentation.forgotpassword.event.InitiatePasswordResetUiEvent
import com.newton.auth.presentation.forgotpassword.state.InitiatePasswordResetUiState
import com.newton.auth.presentation.forgotpassword.view.components.ForgotPasswordForm
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.theme.backgroundGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    uiState: InitiatePasswordResetUiState,
    onEvent: (InitiatePasswordResetUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Reset Password",
                        style = MaterialTheme.typography.headlineSmall.copy(
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient())
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .imePadding(),
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 24.dp),
                ) {
                    Text(
                        text = "Forgot Your Password?",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Don't worry! Enter your email address and we'll send you a reset link to get back into your account.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2,
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                ForgotPasswordForm(
                    onEmailChange = { email ->
                        onEvent(InitiatePasswordResetUiEvent.OnEmailChanged(email))
                    },
                    enabled = !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    emailError = uiState.emailError,
                    email = uiState.email,
                )

                Spacer(modifier = Modifier.height(32.dp))

                PrimaryButton(
                    text = if (uiState.isLoading) "Sending Reset Link..." else "Send Reset Link",
                    onClick = {
                        onEvent(InitiatePasswordResetUiEvent.OnInitiatePasswordResetClicked)
                    },
                    enabled = uiState.isFormValid && !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                )

                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                AlreadyOrDontHaveAccountSection(
                    titleText = "Remember your password?",
                    buttonText = "Back to Sign In",
                    onNavigate = onNavigateToLogin,
                    enabled = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}