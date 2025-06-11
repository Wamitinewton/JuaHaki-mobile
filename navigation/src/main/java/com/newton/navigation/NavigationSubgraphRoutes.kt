package com.newton.navigation

sealed class NavigationSubgraphRoutes(val route: String) {
    data object AuthSubgraph: NavigationSubgraphRoutes(route = "/auth_")
}