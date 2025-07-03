package com.newton.auth.presentation.login.view

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
import androidx.compose.ui.unit.dp
import com.newton.auth.presentation.components.AlreadyOrDontHaveAccountSection
import com.newton.auth.presentation.components.GoogleSignInButton
import com.newton.auth.presentation.components.OrDivider
import com.newton.auth.presentation.components.WelcomeSection
import com.newton.auth.presentation.login.event.LoginUiEvent
import com.newton.auth.presentation.login.state.LoginUiState
import com.newton.auth.presentation.login.view.components.LoginForm
import com.newton.auth.presentation.oauth.event.OAuthUiEvent
import com.newton.auth.presentation.oauth.state.OAuthUiState
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.theme.backgroundGradient
import com.newton.core.enums.OAuthProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginUiState: LoginUiState,
    oAuthUiState: OAuthUiState,
    onLoginEvent: (LoginUiEvent) -> Unit,
    onOAuthEvent: (OAuthUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isLoading = loginUiState.isLoading || oAuthUiState.isLoading || oAuthUiState.isAuthenticating

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Sign In",
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
                WelcomeSection(
                    modifier = Modifier.padding(vertical = 16.dp),
                    title = "Welcome Back",
                    description = "Sign in to your account to continue staying connected with your community and civic engagement.",
                )

                GoogleSignInButton(
                    onClick = {
                        onOAuthEvent(OAuthUiEvent.OnOAuthSignInClicked(OAuthProvider.GOOGLE))
                    },
                    enabled = !isLoading,
                    text = if (oAuthUiState.isLoading) "Preparing Google Sign-In..."
                    else if (oAuthUiState.isAuthenticating) "Completing Sign-In..."
                    else "Sign in with Google",
                    modifier = Modifier.fillMaxWidth(),
                )

                if (oAuthUiState.isLoading || oAuthUiState.isAuthenticating) {
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

                OrDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                )

                LoginForm(
                    email = loginUiState.email,
                    password = loginUiState.password,
                    isPasswordVisible = loginUiState.isPasswordVisible,
                    onEmailChange = { onLoginEvent(LoginUiEvent.OnEmailChanged(it)) },
                    onPasswordChange = { onLoginEvent(LoginUiEvent.OnPasswordChanged(it)) },
                    onTogglePasswordVisibility = { onLoginEvent(LoginUiEvent.OnTogglePasswordVisibility) },
                    onForgotPasswordClick = { onLoginEvent(LoginUiEvent.OnNavigateToForgotPassword) },
                    enabled = !isLoading,
                    loginError = loginUiState.loginError,
                    onClearError = { onLoginEvent(LoginUiEvent.OnClearError) },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = if (loginUiState.isLoading) "Signing In..." else "Sign In",
                    onClick = { onLoginEvent(LoginUiEvent.OnLoginClicked) },
                    enabled = loginUiState.isFormValid && !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                )

                if (loginUiState.isLoading) {
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

                AlreadyOrDontHaveAccountSection(
                    titleText = "Don't have an account?",
                    buttonText = "Sign Up",
                    onNavigate = onNavigateToSignUp,
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}