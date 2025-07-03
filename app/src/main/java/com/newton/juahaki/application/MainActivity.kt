package com.newton.juahaki.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.newton.juahaki.navigation.NavigationSubGraphs
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationSubGraphs: NavigationSubGraphs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        handleOAuthResult()

        setContent {
            val navController = rememberNavController()

            val navigateToRoute = intent.getStringExtra("navigate_to")
            if (navigateToRoute != null) {
                navController.navigate(navigateToRoute) {
                    popUpTo(0) { inclusive = true }
                }
            }

            RootScreen(
                navigationSubGraphs = navigationSubGraphs,
                navController
            )
        }
    }

    private fun handleOAuthResult() {
        val oauthSuccess = intent.getBooleanExtra("oauth_success", false)
        val oauthMessage = intent.getStringExtra("oauth_message")

        if (intent.hasExtra("oauth_success")) {
            if (oauthSuccess) {
                Timber.d("OAuth authentication successful: $oauthMessage")
            } else {
                Timber.e("OAuth authentication failed: $oauthMessage")
            }
        }
    }
}