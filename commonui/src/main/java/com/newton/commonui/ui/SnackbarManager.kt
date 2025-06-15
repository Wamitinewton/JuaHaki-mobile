package com.newton.commonui.ui

import androidx.compose.material3.SnackbarDuration
import com.newton.core.enums.SnackbarType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarManager @Inject constructor(
) {
    private val _snackbarState = MutableStateFlow<SnackbarData?>(null)
    val snackbarState: StateFlow<SnackbarData?> = _snackbarState.asStateFlow()

    fun showSnackbar(snackbarData: SnackbarData) {
        _snackbarState.value = snackbarData
    }

    fun showSuccess(
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Short,
        actionLabel: String? = null,
        onActionClick: (() -> Unit)? = null
    ) {
        showSnackbar(
            SnackbarData(
                message = message,
                type = SnackbarType.SUCCESS,
                duration = duration,
                actionLabel = actionLabel,
                onActionClick = onActionClick
            )
        )
    }

    fun showError(
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Long,
        actionLabel: String? = null,
        onActionClick: (() -> Unit)? = null
    ) {
        showSnackbar(
            SnackbarData(
                message = message,
                type = SnackbarType.ERROR,
                duration = duration,
                actionLabel = actionLabel,
                onActionClick = onActionClick
            )
        )
    }

    fun showInfo(
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Short,
        actionLabel: String? = null,
        onActionClick: (() -> Unit)? = null
    ) {
        showSnackbar(
            SnackbarData(
                message = message,
                type = SnackbarType.INFO,
                duration = duration,
                actionLabel = actionLabel,
                onActionClick = onActionClick
            )
        )
    }

    fun showWarning(
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Short,
        actionLabel: String? = null,
        onActionClick: (() -> Unit)? = null
    ) {
        showSnackbar(
            SnackbarData(
                message = message,
                type = SnackbarType.WARNING,
                duration = duration,
                actionLabel = actionLabel,
                onActionClick = onActionClick
            )
        )
    }

    fun dismissSnackbar() {
        _snackbarState.value = null
    }
}