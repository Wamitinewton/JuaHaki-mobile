package com.newton.quiz.di

import com.newton.quiz.navigation.QuizNavigationApi
import com.newton.quiz.navigation.QuizNavigationApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class QuizNavigationModule {

    @Binds
    @Singleton
    abstract fun bindQuizNavigationApi(quizNavigationApiImpl: QuizNavigationApiImpl): QuizNavigationApi
}