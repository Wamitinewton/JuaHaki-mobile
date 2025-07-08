package com.newton.juahaki.di

import android.content.Context
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.newton.juahaki.worker.CustomWorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {
    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context,
    ): WorkManager = WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideWorkerFactory(customWorkerFactory: CustomWorkerFactory): WorkerFactory = customWorkerFactory
}
