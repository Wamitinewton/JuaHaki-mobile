package com.newton.data.di

import com.newton.data.remote.auth.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }
}