package com.newton.core.ui

import com.newton.core.enums.SnackbarType

data class SnackbarData(
    val message: String,
    val type: SnackbarType = SnackbarType.INFO,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val actionLabel: String? = null,
    val onActionClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)