package com.newton.juahaki.application

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.newton.commonui.components.AppSnackbarHost
import com.newton.commonui.theme.JuaHakiTheme
import com.newton.commonui.ui.SnackbarManager
import com.newton.juahaki.di.SnackbarManagerEntryPoint
import com.newton.juahaki.navigation.JuaHakiNavigation
import com.newton.juahaki.navigation.NavigationSubGraphs
import dagger.hilt.android.EntryPointAccessors

@Composable
fun RootScreen(
    navigationSubGraphs: NavigationSubGraphs,
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val snackbarManager = EntryPointAccessors
        .fromApplication(context, SnackbarManagerEntryPoint::class.java)
        .snackBarManager()

    JuaHakiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(
                snackbarHost = {
                    AppSnackbarHost(snackbarManager = snackbarManager)
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    JuaHakiNavigation(
                        navigationSubGraphs = navigationSubGraphs,
                        navHostController = navController
                    )
                }
            }
        }
    }
}