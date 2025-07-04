package com.newton.home.di

import com.newton.home.navigation.HomeNavigationApi
import com.newton.home.navigation.HomeNavigationApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeNavigationModule {
    @Binds
    @Singleton
    abstract fun bindHomeNavigationApi(homeNavigationApiImpl: HomeNavigationApiImpl): HomeNavigationApi
}
