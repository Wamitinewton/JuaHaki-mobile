package com.newton.data.di

import com.newton.data.remote.auth.AuthApiService
import com.newton.data.remote.auth.UserApiService
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

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }
}