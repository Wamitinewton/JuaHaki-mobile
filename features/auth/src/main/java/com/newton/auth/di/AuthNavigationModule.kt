package com.newton.auth.di

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.auth.navigation.AuthNavigationApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthNavigationModule {
    @Binds
    @Singleton
    abstract fun bindAuthNavigationApi(authNavigationApiImpl: AuthNavigationApiImpl): AuthNavigationApi
}
