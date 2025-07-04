package com.newton.auth.di

import android.content.Context
import com.newton.auth.data.repository.AuthRepositoryImpl
import com.newton.auth.data.repository.OAuthRepositoryImpl
import com.newton.data.remote.auth.AuthApiService
import com.newton.data.remote.auth.OAuthApiService
import com.newton.data.remote.auth.UserApiService
import com.newton.database.dao.UserDao
import com.newton.database.sessionmanager.SessionManager
import com.newton.domain.repository.auth.AuthRepository
import com.newton.domain.repository.oauth.OAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        userDao: UserDao,
    ): AuthRepository =
        AuthRepositoryImpl(
            authApiService = authApiService,
            sessionManager = sessionManager,
            userDao = userDao,
            userApiService = userApiService,
        )

    @Provides
    @Singleton
    fun provideOAuthRepositoryModule(
        oAuthApiService: OAuthApiService,
        sessionManager: SessionManager,
        userDao: UserDao,
        @ApplicationContext context: Context,
    ): OAuthRepository =
        OAuthRepositoryImpl(
            oAuthApiService = oAuthApiService,
            sessionManager = sessionManager,
            userDao = userDao,
            context = context,
        )
}
