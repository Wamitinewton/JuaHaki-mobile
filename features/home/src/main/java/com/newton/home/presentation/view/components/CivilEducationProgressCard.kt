package com.newton.home.presentation.view.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.BodyMediumText
import com.newton.commonui.components.FeatureCard
import com.newton.commonui.components.LabelMediumText
import com.newton.commonui.components.LabelSmallText
import com.newton.commonui.components.TitleMediumText
import com.newton.commonui.theme.AppDimensions

@Composable
fun CivilEducationProgressCard(
    stats: CivilEducationStats,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {},
) {
    FeatureCard(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimensions.Padding.screen,
                    vertical = AppDimensions.Padding.small,
                ),
        onClick = onCardClick,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.Padding.xl),
        ) {
            CivilEducationCardHeader(
                level = stats.level,
                totalPoints = stats.totalPoints,
            )

            Spacer(modifier = Modifier.height(AppDimensions.Spacing.large))

            CivilEducationProgressSection(
                progressToNextLevel = stats.progressToNextLevel,
                averageScore = stats.averageScore,
            )

            Spacer(modifier = Modifier.height(AppDimensions.Spacing.large))

            CivilEducationStatsRow(
                dailyQuizzesCompleted = stats.dailyQuizzesCompleted,
                totalQuizzesAvailable = stats.totalQuizzesAvailable,
                currentStreak = stats.currentStreak,
            )
        }
    }
}

/**
 * Header section of the civil education card
 */
@Composable
private fun CivilEducationCardHeader(
    level: String,
    totalPoints: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            TitleMediumText(
                text = "Civil Education Progress",
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(AppDimensions.Spacing.xs))
            BodyMediumText(
                text = "Level: $level",
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Box(
            modifier =
                Modifier
                    .background(
                        brush =
                            Brush.horizontalGradient(
                                colors =
                                    listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.primaryContainer,
                                    ),
                            ),
                        shape = CircleShape,
                    ).padding(
                        horizontal = AppDimensions.Padding.medium,
                        vertical = AppDimensions.Padding.small,
                    ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "Points",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(AppDimensions.IconSize.small),
                )
                Spacer(modifier = Modifier.width(AppDimensions.Spacing.xs))
                LabelMediumText(
                    text = "$totalPoints pts",
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

/**
 * Progress section with circular progress and average score
 */
@Composable
private fun CivilEducationProgressSection(
    progressToNextLevel: Float,
    averageScore: Float,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator(
                progress = progressToNextLevel,
                size = 80.dp,
                strokeWidth = AppDimensions.Spacing.small,
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                progressColor = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(AppDimensions.Spacing.small))
            LabelSmallText(
                text = "Next Level",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            LabelMediumText(
                text = "${(progressToNextLevel * 100).toInt()}%",
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(80.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            shape = CircleShape,
                        ),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TitleMediumText(
                        text = "${(averageScore * 100).toInt()}%",
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                        contentDescription = "Average Score",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(AppDimensions.IconSize.small),
                    )
                }
            }
            Spacer(modifier = Modifier.height(AppDimensions.Spacing.small))
            LabelSmallText(
                text = "Avg Score",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun CivilEducationStatsRow(
    dailyQuizzesCompleted: Int,
    totalQuizzesAvailable: Int,
    currentStreak: Int,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LabelMediumText(
                text = "Today's Progress",
                color = MaterialTheme.colorScheme.onSurface,
            )
            LabelMediumText(
                text = "$dailyQuizzesCompleted/$totalQuizzesAvailable quizzes",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Spacer(modifier = Modifier.height(AppDimensions.Spacing.small))

        LinearProgressIndicator(
            progress = {
                if (totalQuizzesAvailable > 0) {
                    dailyQuizzesCompleted.toFloat() / totalQuizzesAvailable.toFloat()
                } else {
                    0f
                }
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(AppDimensions.Spacing.small)
                    .clip(RoundedCornerShape(AppDimensions.CornerRadius.xs)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = StrokeCap.Butt,
        )

        Spacer(modifier = Modifier.height(AppDimensions.Spacing.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LabelMediumText(
                text = "Current Streak",
                color = MaterialTheme.colorScheme.onSurface,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TitleMediumText(
                    text = "🔥",
                )
                Spacer(modifier = Modifier.width(AppDimensions.Spacing.xs))
                LabelMediumText(
                    text = "$currentStreak days",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Composable
private fun CircularProgressIndicator(
    progress: Float,
    size: Dp,
    strokeWidth: Dp,
    backgroundColor: Color,
    progressColor: Color,
    modifier: Modifier = Modifier,
) {
    var animatedProgress by remember { mutableFloatStateOf(0f) }

    val animatedProgressState by animateFloatAsState(
        targetValue = animatedProgress,
        animationSpec = tween(durationMillis = 1000),
        label = "progress_animation",
    )

    LaunchedEffect(progress) {
        animatedProgress = progress
    }

    Canvas(
        modifier = modifier.size(size),
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        drawCircularProgressIndicator(
            progress = animatedProgressState,
            strokeWidth = strokeWidthPx,
            backgroundColor = backgroundColor,
            progressColor = progressColor,
        )
    }
}

private fun DrawScope.drawCircularProgressIndicator(
    progress: Float,
    strokeWidth: Float,
    backgroundColor: Color,
    progressColor: Color,
) {
    val diameter = size.minDimension
    val radius = diameter / 2f
    val topLeft =
        Offset(
            (size.width - diameter) / 2f,
            (size.height - diameter) / 2f,
        )
    val size = Size(diameter, diameter)

    drawArc(
        color = backgroundColor,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        topLeft = topLeft,
        size = size,
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
    )

    val sweepAngle = progress * 360f
    drawArc(
        color = progressColor,
        startAngle = -90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = topLeft,
        size = size,
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
    )
}
