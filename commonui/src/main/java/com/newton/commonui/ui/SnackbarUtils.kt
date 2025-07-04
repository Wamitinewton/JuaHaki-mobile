package com.newton.commonui.ui

import com.newton.core.utils.SnackbarUiEffect

fun SnackbarUiEffect.ShowSnackbar.toSnackbarData(): SnackbarData =
    SnackbarData(
        message = this.message,
        type = this.type,
        duration = this.duration,
        actionLabel = this.actionLabel,
        onActionClick = this.onActionClick,
    )

fun SnackbarManager.showSnackbar(effect: SnackbarUiEffect.ShowSnackbar) {
    showSnackbar(effect.toSnackbarData())
}
