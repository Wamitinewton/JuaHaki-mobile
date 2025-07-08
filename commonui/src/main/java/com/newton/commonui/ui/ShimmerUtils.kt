package com.newton.commonui.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.newton.core.enums.ShimmerDirection

fun Modifier.shimmerEffect(
    colors: List<Color>? = null,
    durationMs: Int = 1200,
    direction: ShimmerDirection = ShimmerDirection.LeftToRight,
): Modifier =
    composed {
        val defaultColors =
            listOf(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            )

        val shimmerColors = colors ?: defaultColors

        val transition = rememberInfiniteTransition(label = "shimmer_transition")
        val translateAnimation by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMs, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart,
                ),
            label = "shimmer_animation",
        )

        val brush =
            when (direction) {
                ShimmerDirection.LeftToRight ->
                    Brush.linearGradient(
                        colors = shimmerColors,
                        start = Offset(translateAnimation - 200f, 0f),
                        end = Offset(translateAnimation, 0f),
                    )

                ShimmerDirection.TopToBottom ->
                    Brush.linearGradient(
                        colors = shimmerColors,
                        start = Offset(0f, translateAnimation - 200f),
                        end = Offset(0f, translateAnimation),
                    )

                ShimmerDirection.RightToLeft ->
                    Brush.linearGradient(
                        colors = shimmerColors,
                        start = Offset(translateAnimation, 0f),
                        end = Offset(translateAnimation - 200f, 0f),
                    )

                ShimmerDirection.BottomToTop ->
                    Brush.linearGradient(
                        colors = shimmerColors,
                        start = Offset(0f, translateAnimation),
                        end = Offset(0f, translateAnimation - 200f),
                    )
            }

        background(brush)
    }

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    colors: List<Color>? = null,
    durationMs: Int = 1200,
    direction: ShimmerDirection = ShimmerDirection.LeftToRight,
) {
    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(cornerRadius))
                .shimmerEffect(colors, durationMs, direction),
    )
}

@Composable
fun ShimmerText(
    modifier: Modifier = Modifier,
    height: Dp = 16.dp,
    width: Dp = 120.dp,
    cornerRadius: Dp = 4.dp,
    colors: List<Color>? = null,
) {
    ShimmerBox(
        modifier =
            modifier
                .height(height)
                .width(width),
        cornerRadius = cornerRadius,
        colors = colors,
    )
}

@Composable
fun ShimmerCircle(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    colors: List<Color>? = null,
) {
    Box(
        modifier =
            modifier
                .size(size)
                .clip(RoundedCornerShape(size / 2))
                .shimmerEffect(colors = colors),
    )
}

/**
 * Shimmer card for complex layouts
 */
@Composable
fun ShimmerCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp,
    colors: List<Color>? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier =
            modifier
                .clip(RoundedCornerShape(cornerRadius))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        content()
    }
}
