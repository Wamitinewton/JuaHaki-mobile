package com.newton.navigation

sealed class NavigationSubgraphRoutes(
    val route: String,
) {
    data object AuthSubgraph : NavigationSubgraphRoutes(route = "/auth_")

    data object HomeSubgraph : NavigationSubgraphRoutes(route = "/home_")

    data object QuizSubgraph : NavigationSubgraphRoutes(route = "quiz_")
}
