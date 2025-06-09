package com.newton.juahaki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.newton.auth.presentation.splash.SplashScreen
import com.newton.commonui.theme.JuaHakiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JuaHakiTheme {
                SplashScreen(
                    onSplashComplete = {},
                )
            }
        }
    }
}
