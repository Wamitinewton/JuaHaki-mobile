package com.newton.commonui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.newton.commonui.ui.SnackbarColors
import com.newton.core.enums.SnackbarType

@Composable
fun CustomSnackbar(
    snackbarData: SnackbarData,
    customSnackbarData: com.newton.commonui.ui.SnackbarData,
    modifier: Modifier = Modifier,
) {
    val icon = getIconForType(customSnackbarData.type)

    Snackbar(
        modifier = modifier,
        containerColor = SnackbarColors.getBackgroundColor(customSnackbarData.type),
        contentColor = SnackbarColors.getContentColor(customSnackbarData.type),
        action =
            customSnackbarData.actionLabel?.let { actionLabel ->
                {
                    TextButton(
                        onClick = {
                            customSnackbarData.onActionClick?.invoke()
                            snackbarData.dismiss()
                        },
                    ) {
                        Text(
                            text = actionLabel,
                            color = SnackbarColors.getActionColor(customSnackbarData.type),
                        )
                    }
                }
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = SnackbarColors.getContentColor(customSnackbarData.type),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = customSnackbarData.message,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun getIconForType(type: SnackbarType): ImageVector =
    when (type) {
        SnackbarType.SUCCESS -> Icons.Default.CheckCircle
        SnackbarType.ERROR -> Icons.Default.Error
        SnackbarType.WARNING -> Icons.Default.Warning
        SnackbarType.INFO -> Icons.Default.Info
    }
