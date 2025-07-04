package com.newton.juahaki.navigation

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.home.navigation.HomeNavigationApi
import com.newton.quiz.navigation.QuizNavigationApi

data class NavigationSubGraphs(
    val authNavigationApi: AuthNavigationApi,
    val homeNavigationApi: HomeNavigationApi,
    val quizNavigationApi: QuizNavigationApi,
)
