package com.newton.juahaki.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.HowToVote
import androidx.compose.ui.graphics.vector.ImageVector
import com.newton.navigation.NavigationSubgraphRoutes

sealed class Screens(
    var route: String,
    var selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    var title: String,
) {
    data object Home : Screens(
        NavigationSubgraphRoutes.HomeSubgraph.route,
        Icons.Filled.Home,
        Icons.Outlined.Home,
        "Home",
    )

    // Civic-themed screens for demo purposes
    data object Rights : Screens(
        NavigationSubgraphRoutes.HomeSubgraph.route, // Using home route for now
        Icons.Filled.Gavel,
        Icons.Outlined.Gavel,
        "Rights",
    )

    data object Government : Screens(
        NavigationSubgraphRoutes.HomeSubgraph.route, // Using home route for now
        Icons.Filled.AccountBalance,
        Icons.Outlined.AccountBalance,
        "Gov",
    )

    data object Voting : Screens(
        NavigationSubgraphRoutes.HomeSubgraph.route, // Using home route for now
        Icons.Filled.HowToVote,
        Icons.Outlined.HowToVote,
        "Vote",
    )
}

var bottomNavigationDestination =
    listOf(
        Screens.Home,
        Screens.Rights,
        Screens.Government,
        Screens.Voting,
    )
