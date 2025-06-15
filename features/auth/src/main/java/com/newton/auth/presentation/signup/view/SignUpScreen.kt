package com.newton.auth.presentation.signup.view

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
import com.newton.auth.presentation.components.PrivacyTermsText
import com.newton.auth.presentation.components.WelcomeSection
import com.newton.auth.presentation.signup.event.SignupUiEvent
import com.newton.auth.presentation.signup.state.SignupUiState
import com.newton.auth.presentation.signup.view.components.SignUpForm
import com.newton.auth.presentation.signup.view.components.SignUpFormData
import com.newton.auth.presentation.signup.view.components.SignUpFormErrors
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.theme.backgroundGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    uiState: SignupUiState,
    onEvent: (SignupUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onSignUpWithGoogle: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Create Account",
                        style =
                            MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                            ),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (!uiState.isLoading) {
                                onEvent(SignupUiEvent.OnNavigateBack)
                            }
                        },
                    ) {
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
                    title = "Join Our Community",
                    description = "Create your account to start making a difference in your community and stay informed about civic matters that matter to you.",
                )

                GoogleSignInButton(
                    onClick = onSignUpWithGoogle,
                    enabled = !uiState.isLoading,
                    text = "Sign up with Google",
                    modifier = Modifier.fillMaxWidth(),
                )

                OrDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                )

                SignUpForm(
                    formData =
                        SignUpFormData(
                            email = uiState.email,
                            phone = uiState.phoneNumber,
                            password = uiState.password,
                            firstName = uiState.firstName,
                            lastName = uiState.lastName,
                            username = uiState.username,
                        ),
                    onFormDataChange = { newFormData ->
                        if (newFormData.email != uiState.email) {
                            onEvent(SignupUiEvent.OnEmailChanged(newFormData.email))
                        }
                        if (newFormData.phone != uiState.phoneNumber) {
                            onEvent(SignupUiEvent.OnPhoneNumberChanged(newFormData.phone))
                        }
                        if (newFormData.password != uiState.password) {
                            onEvent(SignupUiEvent.OnPasswordChanged(newFormData.password))
                        }
                        if (newFormData.firstName != uiState.firstName) {
                            onEvent(SignupUiEvent.OnFirstNameChanged(newFormData.firstName))
                        }
                        if (newFormData.lastName != uiState.lastName) {
                            onEvent(SignupUiEvent.OnLastNameChanged(newFormData.lastName))
                        }
                        if (newFormData.username != uiState.username) {
                            onEvent(SignupUiEvent.OnUsernameChanged(newFormData.username))
                        }
                    },
                    errors =
                        SignUpFormErrors(
                            email = uiState.emailError?.message,
                            phone = uiState.phoneNumberError?.message,
                            password = uiState.passwordError?.message,
                            firstName = uiState.firstNameError?.message,
                            lastName = uiState.lastNameError?.message,
                            username = uiState.usernameError?.message,
                        ),
                    enabled = !uiState.isLoading,
                    isPasswordVisible = uiState.isPasswordVisible,
                    onTogglePasswordVisibility = {
                        onEvent(SignupUiEvent.OnTogglePasswordVisibility)
                    },
                    onFieldValidation = { fieldType ->
                        when (fieldType) {
                            "email" -> onEvent(SignupUiEvent.OnValidateEmail)
                            "phone" -> onEvent(SignupUiEvent.OnValidatePhoneNumber)
                            "password" -> onEvent(SignupUiEvent.OnValidatePassword)
                            "firstName" -> onEvent(SignupUiEvent.OnValidateFirstName)
                            "lastName" -> onEvent(SignupUiEvent.OnValidateLastName)
                            "username" -> onEvent(SignupUiEvent.OnValidateUsername)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrivacyTermsText(
                    onPrivacyPolicyClick = onPrivacyPolicyClick,
                    onTermsOfServiceClick = onTermsOfServiceClick,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                PrimaryButton(
                    text = if (uiState.isLoading) "Creating Account..." else "Create Account",
                    onClick = {
                        onEvent(SignupUiEvent.OnSignupClicked)
                    },
                    enabled = uiState.isFormValid && !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                )

                if (uiState.isLoading) {
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

                AlreadyOrDontHaveAccountSection(
                    titleText = "Already have an account?",
                    buttonText = "Sign In",
                    onNavigate = onNavigateToLogin,
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
