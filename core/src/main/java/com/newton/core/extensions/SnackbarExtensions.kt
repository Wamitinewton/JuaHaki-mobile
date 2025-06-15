package com.newton.core.extensions

import androidx.compose.material3.SnackbarDuration
import com.newton.core.enums.SnackbarType
import com.newton.core.utils.SnackbarUiEffect
import kotlinx.coroutines.channels.Channel

suspend fun <T : SnackbarUiEffect> Channel<T>.sendSuccessSnackbar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Short,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    @Suppress("UNCHECKED_CAST")
    send(
        SnackbarUiEffect.ShowSnackbar(
            message = message,
            type = SnackbarType.SUCCESS,
            duration = duration,
            actionLabel = actionLabel,
            onActionClick = onActionClick
        ) as T
    )
}

suspend fun <T : SnackbarUiEffect> Channel<T>.sendErrorSnackbar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Long,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    @Suppress("UNCHECKED_CAST")
    send(
        SnackbarUiEffect.ShowSnackbar(
            message = message,
            type = SnackbarType.ERROR,
            duration = duration,
            actionLabel = actionLabel,
            onActionClick = onActionClick
        ) as T
    )
}

suspend fun <T : SnackbarUiEffect> Channel<T>.sendWarningSnackbar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Short,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    @Suppress("UNCHECKED_CAST")
    send(
        SnackbarUiEffect.ShowSnackbar(
            message = message,
            type = SnackbarType.WARNING,
            duration = duration,
            actionLabel = actionLabel,
            onActionClick = onActionClick
        ) as T
    )
}

suspend fun <T : SnackbarUiEffect> Channel<T>.sendInfoSnackbar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Short,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    @Suppress("UNCHECKED_CAST")
    send(
        SnackbarUiEffect.ShowSnackbar(
            message = message,
            type = SnackbarType.INFO,
            duration = duration,
            actionLabel = actionLabel,
            onActionClick = onActionClick
        ) as T
    )
}

suspend fun <T : SnackbarUiEffect> Channel<T>.sendToast(message: String) {
    @Suppress("UNCHECKED_CAST")
    send(SnackbarUiEffect.ShowToast(message) as T)
}