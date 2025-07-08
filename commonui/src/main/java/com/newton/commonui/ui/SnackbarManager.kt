package com.newton.commonui.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarManager
    @Inject
    constructor() {
        private val _snackbarState = MutableStateFlow<SnackbarData?>(null)
        val snackbarState: StateFlow<SnackbarData?> = _snackbarState.asStateFlow()

        fun showSnackbar(snackbarData: SnackbarData) {
            _snackbarState.value = snackbarData
        }

        fun dismissSnackbar() {
            _snackbarState.value = null
        }
    }
