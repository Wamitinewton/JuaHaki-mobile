package com.newton.auth.di

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.auth.navigation.AuthNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNavigationModule {
    @Provides
    @Singleton
    fun provideAuthNavigationApi(): AuthNavigationApi = AuthNavigationApiImpl()
}
