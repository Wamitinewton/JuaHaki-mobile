package com.newton.juahaki.application

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.newton.commonui.components.AppSnackbarHost
import com.newton.commonui.theme.JuaHakiTheme
import com.newton.juahaki.di.SnackbarManagerEntryPoint
import com.newton.juahaki.navigation.BottomNavigationBar
import com.newton.juahaki.navigation.JuaHakiNavigation
import com.newton.juahaki.navigation.NavigationSubGraphs
import com.newton.navigation.NavigationRoutes
import dagger.hilt.android.EntryPointAccessors

@Composable
fun RootScreen(
    navigationSubGraphs: NavigationSubGraphs,
    navHostController: NavHostController,
) {
    val currentBackStackEntryAsState by navHostController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntryAsState?.destination

    val isShowBottomBar =
        when (currentDestination?.route) {
            NavigationRoutes.HomeScreenRoute.route -> true
            else -> false
        }
    val context = LocalContext.current
    val snackbarManager =
        EntryPointAccessors
            .fromApplication(context, SnackbarManagerEntryPoint::class.java)
            .snackBarManager()

    if (currentDestination?.route == NavigationRoutes.HomeScreenRoute.route) {
        BackHandler {
            (context as? Activity)?.finish()
        }
    }

    JuaHakiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(
                snackbarHost = {
                    AppSnackbarHost(snackbarManager = snackbarManager)
                },
                bottomBar = {
                    if (isShowBottomBar) {
                        BottomNavigationBar(
                            navHostController,
                            currentDestination,
                        )
                    }
                },
            ) { paddingValues ->
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                ) {
                    JuaHakiNavigation(
                        navigationSubGraphs = navigationSubGraphs,
                        navHostController = navHostController,
                    )
                }
            }
        }
    }
}
