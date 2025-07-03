package com.newton.juahaki.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.newton.navigation.NavigationSubgraphRoutes

@Composable
fun JuaHakiNavigation(
    navigationSubGraphs: NavigationSubGraphs,
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationSubgraphRoutes.AuthSubgraph.route
    ) {
        navigationSubGraphs.authNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )

        navigationSubGraphs.homeNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )

        navigationSubGraphs.quizNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
    }
    
}