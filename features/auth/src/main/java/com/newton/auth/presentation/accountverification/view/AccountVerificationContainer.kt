package com.newton.auth.presentation.accountverification.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.newton.auth.presentation.accountverification.event.OtpUiEffect
import com.newton.auth.presentation.accountverification.event.OtpUiEvent
import com.newton.auth.presentation.accountverification.viewmodel.AccountVerificationViewModel
import com.newton.commonui.ui.HandleUiEffects
import com.newton.commonui.ui.SnackbarData

@Composable
fun AccountVerificationContainer(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onShowSnackbar: (SnackbarData) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountVerificationViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    HandleUiEffects(
        uiEffects = viewModel.uiEffect,
        onShowSnackbar = onShowSnackbar,
        onCustomEffect = { effect ->
            when (effect) {
                is OtpUiEffect.NavigateBack -> onNavigateBack()
                is OtpUiEffect.NavigateToLogin -> onNavigateToLogin()
            }
        },
    )

    LaunchedEffect(Unit) {
        if (uiState.email.isNotBlank() && uiState.resendCooldownSeconds == 0) {
            viewModel.startInitialCooldown()
        }
    }

    AccountVerificationContainer(
        otpCode = uiState.otp,
        onNavigateBack = { viewModel.onEvent(OtpUiEvent.OnNavigateBack) },
        onOtpValueChange = { otp -> viewModel.onEvent(OtpUiEvent.OnOtpChanged(otp)) },
        onVerifyOtp = { viewModel.onEvent(OtpUiEvent.OnVerifyOtpClicked) },
        onResendCode = { viewModel.onEvent(OtpUiEvent.OnResendOtpClicked) },
        onOtpComplete = { viewModel.onEvent(OtpUiEvent.OnVerifyOtpClicked) },
        modifier = modifier,
        isLoading = uiState.isLoading,
        isResendingCode = uiState.isResendLoading,
        errorMessage = uiState.verificationError,
        isFormValid = uiState.isFormValid,
        isResendButtonEnabled = uiState.canResend && !uiState.isResendLoading && !uiState.isLoading,
        resendCooldownSeconds = uiState.resendCooldownSeconds,
        title = "Verify Your Account",
        subtitle = "Account Verification",
        description = "Enter the 6-digit verification code sent to",
        contactInfo = uiState.email.takeIf { it.isNotBlank() },
        showResendOption = true,
        otpLength = 6,
    )
}
