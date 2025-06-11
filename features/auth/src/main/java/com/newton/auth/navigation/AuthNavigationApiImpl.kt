package com.newton.auth.navigation

import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.auth.presentation.splash.SplashScreen
import com.newton.core.enums.TransitionType
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.navigation.NavigationTransitions

class AuthNavigationApiImpl: AuthNavigationApi {

    private val navigationTransitions = NavigationTransitions()

    override fun registerNavigationGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navHostController: androidx.navigation.NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.AuthSubgraph.route,
            startDestination = NavigationRoutes.SplashScreenRoute.route
        ) {
            composable(
                route = NavigationRoutes.SplashScreenRoute.route,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300)
            ) {
                SplashScreen(
                    onSplashComplete = {}
                )
            }
        }
    }
}