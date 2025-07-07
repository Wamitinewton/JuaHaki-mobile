package com.newton.commonui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CivicLoadingIndicator(
    message: String = "Loading...",
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 80.dp,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "civic_loading")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 2000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            ),
        label = "rotation",
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.size(size),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize(),
            ) {
                drawCivicLoadingRings(
                    rotation = rotation,
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor,
                    tertiaryColor = tertiaryColor,
                )
            }

            Box(
                modifier = Modifier.size(size * 0.3f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "🇰🇪",
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = message,
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                ),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
    }
}

private fun DrawScope.drawCivicLoadingRings(
    rotation: Float,
    primaryColor: Color,
    secondaryColor: Color,
    tertiaryColor: Color,
) {
    val strokeWidth = 4.dp.toPx()
    val center = Offset(size.width / 2, size.height / 2)

    val outerRadius = size.minDimension * 0.4f
    drawCircle(
        color = primaryColor.copy(alpha = 0.3f),
        radius = outerRadius,
        center = center,
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
    )

    val arcStartAngle = rotation
    val arcSweepAngle = 120f
    drawArc(
        color = secondaryColor,
        startAngle = arcStartAngle,
        sweepAngle = arcSweepAngle,
        useCenter = false,
        topLeft =
            Offset(
                center.x - outerRadius,
                center.y - outerRadius,
            ),
        size =
            androidx.compose.ui.geometry
                .Size(outerRadius * 2, outerRadius * 2),
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
    )

    val innerRadius = size.minDimension * 0.25f
    val innerRotation = -rotation * 0.7f
    val innerStartAngle = innerRotation
    val innerSweepAngle = 90f

    drawArc(
        color = tertiaryColor,
        startAngle = innerStartAngle,
        sweepAngle = innerSweepAngle,
        useCenter = false,
        topLeft =
            Offset(
                center.x - innerRadius,
                center.y - innerRadius,
            ),
        size =
            androidx.compose.ui.geometry
                .Size(innerRadius * 2, innerRadius * 2),
        style = Stroke(width = strokeWidth * 0.8f, cap = StrokeCap.Round),
    )
}

@Composable
fun CivicLoadingScreen(
    message: String = "Loading your civic education content...",
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CivicLoadingIndicator(
            message = message,
            size = 120.dp,
        )
    }
}
