package com.newton.auth.presentation.accountverification.view

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.CompactButton
import com.newton.commonui.components.OtpTextField
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.theme.backgroundGradient
import com.newton.core.enums.ButtonVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountVerificationContainer(
    otpCode: String,
    onNavigateBack: () -> Unit,
    onOtpValueChange: (String) -> Unit,
    onVerifyOtp: (String) -> Unit,
    onResendCode: () -> Unit,
    onOtpComplete: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isResendingCode: Boolean = false,
    errorMessage: String? = null,
    isFormValid: Boolean = false,
    isResendButtonEnabled: Boolean = true,
    otpLength: Int = 6,
    resendCooldownSeconds: Int = 0,
    showResendOption: Boolean = true,
    title: String = "Verify Your Code",
    subtitle: String = "We've sent you a verification code",
    description: String = "Enter the code we sent to continue",
    contactInfo: String? = null,
) {
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
                            value = otpCode,
                            onValueChange = onOtpValueChange,
                            otpLength = otpLength,
                            enabled = !isLoading && !isResendingCode,
                            isError = errorMessage != null,
                            onOtpComplete = onOtpComplete,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        errorMessage?.let { error ->
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
                                    isLoading -> "Verifying..."
                                    else -> "Verify Code"
                                },
                            onClick = {
                                if (isFormValid && !isLoading) {
                                    onVerifyOtp(otpCode)
                                }
                            },
                            enabled = isFormValid && !isLoading && !isResendingCode,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        if (isLoading) {
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
                                        isResendingCode -> "Sending..."
                                        resendCooldownSeconds > 0 -> "Resend in ${resendCooldownSeconds}s"
                                        else -> "Resend Code"
                                    },
                                onClick = onResendCode,
                                enabled = isResendButtonEnabled,
                                variant = ButtonVariant.Text,
                                leadingIcon = if (!isResendingCode) Icons.Default.Refresh else null,
                            )

                            if (isResendingCode) {
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
