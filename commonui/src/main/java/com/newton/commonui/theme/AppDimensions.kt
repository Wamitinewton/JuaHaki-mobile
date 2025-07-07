package com.newton.commonui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Centralized dimensions for consistent spacing, sizing, and layout across the app.
 */
object AppDimensions {

    /**
     * Standard spacing values used throughout the app
     */
    object Spacing {
        val xs: Dp = 4.dp          // Extra small spacing
        val small: Dp = 8.dp       // Small spacing
        val medium: Dp = 16.dp     // Medium spacing
        val large: Dp = 24.dp      // Large spacing
        val xl: Dp = 32.dp         // Extra large spacing
        val xxl: Dp = 48.dp        // Extra extra large spacing
    }

    /**
     * Standard padding values for consistent content spacing
     */
    object Padding {
        val xs: Dp = 4.dp          // Minimal padding
        val small: Dp = 8.dp       // Small padding
        val medium: Dp = 12.dp     // Medium padding
        val large: Dp = 16.dp      // Large padding
        val xl: Dp = 20.dp         // Extra large padding
        val xxl: Dp = 24.dp        // Screen edge padding
        val screen: Dp = 16.dp     // Standard screen padding
    }

    /**
     * Standard corner radius values for cards, buttons, and surfaces
     */
    object CornerRadius {
        val xs: Dp = 4.dp          // Small radius for chips
        val small: Dp = 8.dp       // Small radius
        val medium: Dp = 12.dp     // Medium radius
        val large: Dp = 16.dp      // Large radius for cards
        val xl: Dp = 20.dp         // Extra large radius
        val xxl: Dp = 24.dp        // Maximum radius for special cards
        val circular: Dp = 50.dp   // For circular elements
    }

    /**
     * Standard icon sizes for consistent visual hierarchy
     */
    object IconSize {
        val xs: Dp = 12.dp         // Tiny icons
        val small: Dp = 16.dp      // Small icons
        val medium: Dp = 20.dp     // Medium icons
        val large: Dp = 24.dp      // Large icons
        val xl: Dp = 32.dp         // Extra large icons
        val xxl: Dp = 48.dp        // Hero icons
    }

    /**
     * Standard elevation values for consistent depth hierarchy
     */
    object Elevation {
        val none: Dp = 0.dp        // No elevation
        val small: Dp = 1.dp       // Subtle elevation
        val medium: Dp = 4.dp      // Default card elevation
        val large: Dp = 8.dp       // Prominent elevation
        val xl: Dp = 12.dp         // High elevation for modals
        val floating: Dp = 6.dp    // FAB elevation
    }

    /**
     * Standard border widths for outlines and dividers
     */
    object BorderWidth {
        val thin: Dp = 1.dp        // Thin border
        val medium: Dp = 2.dp      // Medium border
        val thick: Dp = 4.dp       // Thick border for emphasis
    }
}