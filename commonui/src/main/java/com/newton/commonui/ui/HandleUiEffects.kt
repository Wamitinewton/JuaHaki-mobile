package com.newton.commonui.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.newton.core.utils.SnackbarUiEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <T : SnackbarUiEffect> HandleUiEffects(
    uiEffects: Flow<T>,
    onShowSnackbar: (SnackbarData) -> Unit,
    onShowToast: ((String) -> Unit)? = null,
    onCustomEffect: ((T) -> Unit)? = null
) {
    LaunchedEffect(Unit) {
        uiEffects.collect { effect ->
            when (effect) {
                is SnackbarUiEffect.ShowSnackbar -> {
                    onShowSnackbar(effect.toSnackbarData())
                }
                is SnackbarUiEffect.ShowToast -> {
                    onShowToast?.invoke(effect.message)
                }
                else -> {
                    onCustomEffect?.invoke(effect)
                }
            }
        }
    }
}