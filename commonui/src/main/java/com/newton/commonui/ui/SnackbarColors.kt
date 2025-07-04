package com.newton.commonui.ui

import androidx.compose.material3.SnackbarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.newton.core.enums.SnackbarType

object SnackbarColors {
    @Composable
    fun getBackgroundColor(type: SnackbarType): Color =
        when (type) {
            SnackbarType.SUCCESS -> Color(0xFF4CAF50)
            SnackbarType.ERROR -> Color(0xFFF44336)
            SnackbarType.WARNING -> Color(0xFFFF9800)
            SnackbarType.INFO -> SnackbarDefaults.color
        }

    @Composable
    fun getContentColor(type: SnackbarType): Color =
        when (type) {
            SnackbarType.SUCCESS -> Color.White
            SnackbarType.ERROR -> Color.White
            SnackbarType.WARNING -> Color.White
            SnackbarType.INFO -> SnackbarDefaults.contentColor
        }

    @Composable
    fun getActionColor(type: SnackbarType): Color =
        when (type) {
            SnackbarType.SUCCESS -> Color.White.copy(alpha = 0.9f)
            SnackbarType.ERROR -> Color.White.copy(alpha = 0.9f)
            SnackbarType.WARNING -> Color.White.copy(alpha = 0.9f)
            SnackbarType.INFO -> SnackbarDefaults.actionColor
        }
}
