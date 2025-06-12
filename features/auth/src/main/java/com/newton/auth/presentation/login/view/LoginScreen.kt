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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.auth.presentation.components.AlreadyOrDontHaveAccountSection
import com.newton.auth.presentation.components.GoogleSignInButton
import com.newton.auth.presentation.components.OrDivider
import com.newton.auth.presentation.components.PrivacyTermsText
import com.newton.auth.presentation.components.WelcomeSection
import com.newton.auth.presentation.login.state.LoginScreenState
import com.newton.auth.presentation.login.view.components.LoginForm
import com.newton.auth.presentation.login.view.components.LoginFormData
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.theme.backgroundGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onLoginWithEmail: (LoginFormData) -> Unit,
    onLoginWithGoogle: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    var screenState by remember {
        mutableStateOf(LoginScreenState(isLoading = isLoading))
    }

    screenState = screenState.copy(isLoading = isLoading)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Sign In",
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
            ) {
                WelcomeSection(
                    modifier = Modifier.padding(vertical = 16.dp),
                    title = "Welcome Back",
                    description = "Sign in to your account to continue staying connected with your community and civic engagement.",
                )

                GoogleSignInButton(
                    onClick = onLoginWithGoogle,
                    enabled = !screenState.isLoading,
                    text = "Sign in with Google",
                    modifier = Modifier.fillMaxWidth(),
                )

                OrDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                )

                LoginForm(
                    formData = screenState.formData,
                    onFormDataChange = { newFormData ->
                        screenState =
                            screenState.copy(
                                formData = newFormData,
                                isFormValid =
                                    newFormData.emailOrUsername.isNotBlank() &&
                                        newFormData.password.isNotBlank(),
                            )
                    },
                    errors = screenState.formErrors,
                    onForgotPasswordClick = onForgotPasswordClick,
                    enabled = !screenState.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = if (screenState.isLoading) "Signing In..." else "Sign In",
                    onClick = {
                        if (screenState.isFormValid && !screenState.isLoading) {
                            onLoginWithEmail(screenState.formData)
                        }
                    },
                    enabled = screenState.isFormValid && !screenState.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                )

                if (screenState.isLoading) {
                    Box(
                        modifier =
                            Modifier
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

                Spacer(modifier = Modifier.height(16.dp))

                PrivacyTermsText(
                    onPrivacyPolicyClick = onPrivacyPolicyClick,
                    onTermsOfServiceClick = onTermsOfServiceClick,
                    modifier = Modifier.fillMaxWidth(),
                )

                AlreadyOrDontHaveAccountSection(
                    titleText = "Don't have an account?",
                    buttonText = "Sign Up",
                    onNavigate = onNavigateToSignUp,
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
