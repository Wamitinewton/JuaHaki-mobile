package com.newton.juahaki.di

import com.newton.commonui.ui.SnackbarManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SnackbarManagerEntryPoint {
    fun snackBarManager(): SnackbarManager
}