package com.newton.commonui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedChatbotFAB(
    modifier: Modifier = Modifier,
    chatbotIconRes: Int,
    onFabClick: () -> Unit = {},
    isEnabled: Boolean = true,
) {
    val haptic = LocalHapticFeedback.current
    var isPressed by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "gradient_rotation")
    val gradientRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            ),
        label = "gradient_rotation",
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec =
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow,
            ),
        label = "scale_animation",
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val surfaceColor = MaterialTheme.colorScheme.surface

    Box(
        modifier = modifier.size(80.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .size(72.dp)
                    .scale(scale)
                    .pointerInput(Unit) {
                        if (isEnabled) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    tryAwaitRelease()
                                    isPressed = false
                                },
                                onTap = { onFabClick() },
                            )
                        }
                    },
            contentAlignment = Alignment.Center,
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize(),
            ) {
                drawAnimatedGradientBorder(
                    gradientRotation = gradientRotation,
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor,
                    tertiaryColor = tertiaryColor,
                )
            }

            Card(
                modifier = Modifier.size(64.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = CircleShape,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = chatbotIconRes),
                        contentDescription = "Chatbot Assistant",
                        modifier = Modifier.size(32.dp),
                        tint = primaryColor,
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawAnimatedGradientBorder(
    gradientRotation: Float,
    primaryColor: Color,
    secondaryColor: Color,
    tertiaryColor: Color,
) {
    val strokeWidth = 4.dp.toPx()
    val radius = (size.minDimension - strokeWidth) / 2
    val centerPoint = center

    val angleRadians = Math.toRadians(gradientRotation.toDouble())
    val gradientStart =
        Offset(
            x = centerPoint.x + cos(angleRadians).toFloat() * radius,
            y = centerPoint.y + sin(angleRadians).toFloat() * radius,
        )
    val gradientEnd =
        Offset(
            x = centerPoint.x - cos(angleRadians).toFloat() * radius,
            y = centerPoint.y - sin(angleRadians).toFloat() * radius,
        )

    val colors =
        listOf(
            primaryColor,
            secondaryColor,
            tertiaryColor,
            primaryColor.copy(alpha = 0.7f),
            secondaryColor,
            primaryColor,
        )

    val brush =
        Brush.linearGradient(
            colors = colors,
            start = gradientStart,
            end = gradientEnd,
        )

    drawCircle(
        brush = brush,
        radius = radius,
        center = centerPoint,
        style = Stroke(width = strokeWidth),
    )
}
