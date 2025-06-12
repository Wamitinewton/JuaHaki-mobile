package com.newton.auth.presentation.otp.view

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.LaunchedEffect
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
import com.newton.auth.presentation.otp.state.OtpVerificationScreenState
import com.newton.commonui.components.CompactButton
import com.newton.commonui.components.OtpTextField
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.theme.backgroundGradient
import com.newton.core.enums.ButtonVariant
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    onNavigateBack: () -> Unit,
    onVerifyOtp: (String) -> Unit,
    onResendCode: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isResendingCode: Boolean = false,
    errorMessage: String? = null,
    title: String = "Verify Your Code",
    subtitle: String = "We've sent you a verification code",
    description: String = "Enter the code we sent to continue",
    contactInfo: String? = null,
    otpLength: Int = 6,
    resendCooldownSeconds: Int = 60,
    showResendOption: Boolean = true,
) {
    var screenState by remember {
        mutableStateOf(
            OtpVerificationScreenState(
                isLoading = isLoading,
                isResendingCode = isResendingCode,
                errorMessage = errorMessage,
                otpLength = otpLength,
                resendCooldownSeconds = if (isResendingCode || isLoading) resendCooldownSeconds else 0,
            ),
        )
    }

    screenState =
        screenState.copy(
            isLoading = isLoading,
            isResendingCode = isResendingCode,
            errorMessage = errorMessage,
        )

    LaunchedEffect(screenState.isResendingCode) {
        if (screenState.isResendingCode) {
            screenState =
                screenState.copy(
                    resendCooldownSeconds = resendCooldownSeconds,
                    canResend = false,
                )
        }
    }

    LaunchedEffect(screenState.resendCooldownSeconds) {
        if (screenState.resendCooldownSeconds > 0) {
            delay(1000)
            screenState =
                screenState.copy(
                    resendCooldownSeconds = screenState.resendCooldownSeconds - 1,
                )
        } else if (screenState.resendCooldownSeconds == 0 && !screenState.canResend) {
            screenState = screenState.copy(canResend = true)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
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
                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = subtitle,
                        style =
                            MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (contactInfo != null) "$description $contactInfo" else description,
                        style =
                            MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

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
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Enter Verification Code",
                            style =
                                MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                ),
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        OtpTextField(
                            value = screenState.otpCode,
                            onValueChange = { newOtp ->
                                screenState =
                                    screenState.copy(
                                        otpCode = newOtp,
                                        isFormValid = newOtp.length == otpLength,
                                        errorMessage = null,
                                    )
                            },
                            otpLength = otpLength,
                            enabled = !screenState.isLoading && !screenState.isResendingCode,
                            isError = screenState.errorMessage != null,
                            onOtpComplete = { completedOtp ->
                                if (screenState.isFormValid && !screenState.isLoading) {
                                    onVerifyOtp(completedOtp)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )

                        screenState.errorMessage?.let { error ->
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = error,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        PrimaryButton(
                            text =
                                when {
                                    screenState.isLoading -> "Verifying..."
                                    else -> "Verify Code"
                                },
                            onClick = {
                                if (screenState.isFormValid && !screenState.isLoading) {
                                    onVerifyOtp(screenState.otpCode)
                                }
                            },
                            enabled = screenState.isFormValid && !screenState.isLoading && !screenState.isResendingCode,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        if (screenState.isLoading) {
                            Spacer(modifier = Modifier.height(16.dp))
                            CircularProgressIndicator(
                                modifier = Modifier.padding(8.dp),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (showResendOption) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Didn't receive the code?",
                            style =
                                MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                ),
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            CompactButton(
                                text =
                                    when {
                                        screenState.isResendingCode -> "Sending..."
                                        screenState.resendCooldownSeconds > 0 -> "Resend in ${screenState.resendCooldownSeconds}s"
                                        else -> "Resend Code"
                                    },
                                onClick = onResendCode,
                                enabled = screenState.isResendButtonEnabled,
                                variant = ButtonVariant.Text,
                                leadingIcon = if (!screenState.isResendingCode) Icons.Default.Refresh else null,
                            )

                            if (screenState.isResendingCode) {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(4.dp),
                                    color = MaterialTheme.colorScheme.primary,
                                    strokeWidth = 2.dp,
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
