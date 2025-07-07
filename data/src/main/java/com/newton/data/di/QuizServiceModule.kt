package com.newton.data.di

import com.newton.data.remote.QuizApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizServiceModule {
    @Provides
    @Singleton
    fun provideQuizService(retrofit: Retrofit): QuizApiService = retrofit.create(QuizApiService::class.java)
}
