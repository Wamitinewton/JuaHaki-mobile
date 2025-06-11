package com.newton.commonui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val JuaHakiLightColorScheme = lightColorScheme(
    primary = DemocraticGreen,
    onPrimary = PeaceWhite,
    primaryContainer = DemocraticGreenLight,
    onPrimaryContainer = UnityBlack,

    secondary = JusticeBlue,
    onSecondary = PeaceWhite,
    secondaryContainer = JusticeBlueLight,
    onSecondaryContainer = UnityBlack,

    tertiary = TransparencyGold,
    onTertiary = UnityBlack,
    tertiaryContainer = TransparencyGoldLight,
    onTertiaryContainer = UnityBlack,

    error = ErrorRed,
    onError = PeaceWhite,
    errorContainer = ErrorRedLight,
    onErrorContainer = UnityBlack,

    background = BackgroundCream,
    onBackground = TextPrimary,

    surface = SurfacePure,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceLight,
    onSurfaceVariant = TextSecondary,

    outline = DividerGrey,
    outlineVariant = HoverGrey,

    scrim = UnityBlack.copy(alpha = 0.32f),
    inverseSurface = UnityGrey,
    inverseOnSurface = PeaceWhite,
    inversePrimary = DemocraticGreenLight,
    surfaceTint = DemocraticGreen
)

@Composable
fun JuaHakiTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = JuaHakiLightColorScheme

    val view = LocalView.current
    val systemUiController = rememberSystemUiController()
    if (!view.isInEditMode) {
        SideEffect {
            systemUiController.setStatusBarColor(
                color = DemocraticGreen,
                darkIcons = false
            )

            systemUiController.setNavigationBarColor(
                color = Color.Transparent,
                darkIcons = true
            )
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}