package com.newton.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.commonui.ui.SnackbarManager
import com.newton.home.presentation.HomeScreen
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import javax.inject.Inject

class HomeNavigationApiImpl @Inject constructor(
    private val snackbarManager: SnackbarManager
): HomeNavigationApi {
    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.HomeSubgraph.route,
            startDestination = NavigationRoutes.HomeScreenRoute.route
        ) {
            composable(
                route = NavigationRoutes.HomeScreenRoute.route,
            ) {
                HomeScreen(
                    onNavigateToDailyCivicQuiz = {
                        navHostController.navigate(NavigationRoutes.QuizInfoRoute.route)
                    }
                )
            }
        }
    }
}