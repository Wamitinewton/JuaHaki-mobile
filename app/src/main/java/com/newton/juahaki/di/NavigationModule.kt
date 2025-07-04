package com.newton.juahaki.di

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.home.navigation.HomeNavigationApi
import com.newton.juahaki.navigation.NavigationSubGraphs
import com.newton.quiz.navigation.QuizNavigationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    fun provideNavigationSubGraphs(
        authNavigationApi: AuthNavigationApi,
        homeNavigationApi: HomeNavigationApi,
        quizNavigationApi: QuizNavigationApi,
    ): NavigationSubGraphs =
        NavigationSubGraphs(
            authNavigationApi = authNavigationApi,
            homeNavigationApi = homeNavigationApi,
            quizNavigationApi = quizNavigationApi,
        )
}
