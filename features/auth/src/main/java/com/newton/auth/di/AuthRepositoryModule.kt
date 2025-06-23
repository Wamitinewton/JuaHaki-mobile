package com.newton.auth.di

import com.newton.auth.data.repository.AuthRepositoryImpl
import com.newton.data.remote.auth.AuthApiService
import com.newton.data.remote.auth.UserApiService
import com.newton.database.dao.UserDao
import com.newton.database.sessionmanager.SessionManager
import com.newton.domain.repository.auth.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepositoryModule(
        authApiService: AuthApiService,
        userApiService: UserApiService,
        sessionManager: SessionManager,
        userDao: UserDao
    ): AuthRepository {
        return AuthRepositoryImpl(
            authApiService = authApiService,
            sessionManager = sessionManager,
            userDao = userDao,
            userApiService = userApiService
        )
    }
}