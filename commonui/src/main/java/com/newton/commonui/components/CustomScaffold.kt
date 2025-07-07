package com.newton.commonui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.newton.commonui.theme.AppDimensions
import com.newton.commonui.theme.backgroundGradient

/**
 * Universal scaffold component for JuaHaki app that provides consistent layout structure
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CivicScaffold(
    modifier: Modifier = Modifier,

    title: String? = null,
    showTopBar: Boolean = true,
    showNavigationIcon: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    navigationIconContentDescription: String = "Navigate back",
    onNavigationClick: (() -> Unit)? = null,
    topBarActions: @Composable RowScope.() -> Unit = {},
    topBarColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        actionIconContentColor = MaterialTheme.colorScheme.onSurface
    ),

    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,

    // Background Configuration
    useGradientBackground: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    backgroundBrush: Brush? = null,

    // Content Configuration
    contentPadding: PaddingValues = PaddingValues(AppDimensions.Padding.screen),
    applyContentPadding: Boolean = true,

    // Window Insets Configuration
    windowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,

    content: @Composable (PaddingValues) -> Unit
) {
    val finalBackgroundBrush = backgroundBrush ?: if (useGradientBackground) {
        backgroundGradient()
    } else {
        Brush.verticalGradient(listOf(backgroundColor, backgroundColor))
    }

    Scaffold(
        modifier = modifier.background(brush = finalBackgroundBrush),
        topBar = {
            if (showTopBar) {
                CivicTopAppBar(
                    title = title,
                    showNavigationIcon = showNavigationIcon,
                    navigationIcon = navigationIcon,
                    navigationIconContentDescription = navigationIconContentDescription,
                    onNavigationClick = onNavigationClick,
                    actions = topBarActions,
                    colors = topBarColors
                )
            }
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = Color.Transparent,
        contentWindowInsets = windowInsets,
        content = { scaffoldPadding ->
            if (applyContentPadding) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding)
                        .padding(contentPadding)
                ) {
                    content(PaddingValues(0.dp))
                }
            } else {
                content(scaffoldPadding)
            }
        }
    )
}

/**
 * Custom top app bar component with civic theme styling
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CivicTopAppBar(
    title: String?,
    showNavigationIcon: Boolean,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    onNavigationClick: (() -> Unit)?,
    actions: @Composable RowScope.() -> Unit,
    colors: TopAppBarColors,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            if (title != null) {
                TitleLargeText(
                    text = title,
                    color = colors.titleContentColor,
                    maxLines = 1
                )
            }
        },
        navigationIcon = {
            if (showNavigationIcon && onNavigationClick != null) {
                IconButton(
                    onClick = onNavigationClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = colors.navigationIconContentColor
                    )
                ) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationIconContentDescription
                    )
                }
            }
        },
        actions = actions,
        colors = colors,
        modifier = modifier
    )
}
