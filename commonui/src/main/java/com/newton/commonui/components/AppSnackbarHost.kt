package com.newton.commonui.components

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.newton.commonui.ui.SnackbarManager

@Composable
fun AppSnackbarHost(
    snackbarManager: SnackbarManager,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarState by snackbarManager.snackbarState.collectAsState()

    LaunchedEffect(snackbarState) {
        snackbarState?.let { data ->
            val result =
                snackbarHostState.showSnackbar(
                    message = data.message,
                    actionLabel = data.actionLabel,
                    duration = data.duration,
                )

            when (result) {
                androidx.compose.material3.SnackbarResult.Dismissed -> {
                    data.onDismiss?.invoke()
                    snackbarManager.dismissSnackbar()
                }

                androidx.compose.material3.SnackbarResult.ActionPerformed -> {
                    data.onActionClick?.invoke()
                    snackbarManager.dismissSnackbar()
                }
            }
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier,
    ) { snackbarData ->
        snackbarState?.let { customData ->
            CustomSnackbar(
                snackbarData = snackbarData,
                customSnackbarData = customData,
            )
        }
    }
}
