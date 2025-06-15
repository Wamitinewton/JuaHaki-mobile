package com.newton.core.utils

import androidx.compose.material3.SnackbarDuration
import com.newton.core.enums.SnackbarType

/**
 * Base interface for UI effects that can show snackbars
 */
interface SnackbarUiEffect {
    /**
     * Generic snackbar effect that can be used across all features
     */
    data class ShowSnackbar(
        val message: String,
        val type: SnackbarType = SnackbarType.INFO,
        val duration: SnackbarDuration = SnackbarDuration.Short,
        val actionLabel: String? = null,
        val onActionClick: (() -> Unit)? = null
    ) : SnackbarUiEffect

    /**
     * Generic toast effect that can be used across all features
     */
    data class ShowToast(
        val message: String
    ) : SnackbarUiEffect
}