package com.newton.juahaki.application

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.newton.commonui.theme.JuaHakiTheme
import com.newton.juahaki.navigation.JuaHakiNavigation
import com.newton.juahaki.navigation.NavigationSubGraphs

@Composable
fun RootScreen(navigationSubGraphs: NavigationSubGraphs) {
    val navController = rememberNavController()


    JuaHakiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                JuaHakiNavigation(
                    navigationSubGraphs = navigationSubGraphs,
                    navHostController = navController
                )
            }
        }
    }
    
}