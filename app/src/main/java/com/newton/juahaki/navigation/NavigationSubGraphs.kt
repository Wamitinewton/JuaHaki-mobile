package com.newton.juahaki.navigation

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.home.navigation.HomeNavigationApi

data class NavigationSubGraphs(
    val authNavigationApi: AuthNavigationApi,
    val homeNavigationApi: HomeNavigationApi
)
