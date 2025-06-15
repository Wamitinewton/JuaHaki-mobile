package com.newton.auth.extensions

import androidx.compose.material3.SnackbarDuration
import com.newton.auth.presentation.signup.event.SignupUiEffect
import com.newton.commonui.ui.SnackbarData
import com.newton.commonui.ui.SnackbarManager
import com.newton.core.enums.SnackbarType
import kotlinx.coroutines.channels.Channel

suspend fun Channel<SignupUiEffect>.sendSuccessSnackbar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Short,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    send(
        SignupUiEffect.ShowSnackbar(
            message = message,
            type = SnackbarType.SUCCESS,
            duration = duration,
            actionLabel = actionLabel,
            onActionClick = onActionClick
        )
    )
}

suspend fun Channel<SignupUiEffect>.sendErrorSnackbar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Long,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    send(
        SignupUiEffect.ShowSnackbar(
            message = message,
            type = SnackbarType.ERROR,
            duration = duration,
            actionLabel = actionLabel,
            onActionClick = onActionClick
        )
    )
}

suspend fun Channel<SignupUiEffect>.sendWarningSnackbar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Short,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    send(
        SignupUiEffect.ShowSnackbar(
            message = message,
            type = SnackbarType.WARNING,
            duration = duration,
            actionLabel = actionLabel,
            onActionClick = onActionClick
        )
    )
}

suspend fun Channel<SignupUiEffect>.sendInfoSnackbar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Short,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    send(
        SignupUiEffect.ShowSnackbar(
            message = message,
            type = SnackbarType.INFO,
            duration = duration,
            actionLabel = actionLabel,
            onActionClick = onActionClick
        )
    )
}

fun SignupUiEffect.ShowSnackbar.toSnackbarData(): SnackbarData {
    return SnackbarData(
        message = this.message,
        type = this.type,
        duration = this.duration,
        actionLabel = this.actionLabel,
        onActionClick = this.onActionClick
    )
}

fun SnackbarManager.showSnackbar(effect: SignupUiEffect.ShowSnackbar) {
    showSnackbar(effect.toSnackbarData())
}