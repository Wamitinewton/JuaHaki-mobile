package com.newton.quiz.presentation.quizinfo.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.BodyMediumText
import com.newton.commonui.components.BodySmallText
import com.newton.commonui.components.LabelMediumText
import com.newton.commonui.components.TitleLargeText
import com.newton.commonui.theme.AppDimensions
import com.newton.domain.models.quiz.QuizInfo

@Composable
fun QuizHeroSection(
    quizInfo: QuizInfo,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter =
            fadeIn(
                animationSpec = tween(800, easing = FastOutSlowInEasing),
            ) +
                slideInVertically(
                    animationSpec = tween(800, easing = FastOutSlowInEasing),
                    initialOffsetY = { it / 2 },
                ),
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(AppDimensions.CornerRadius.xl),
            elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.large),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
        ) {
            Box {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .heightIn(200.dp)
                            .background(
                                brush =
                                    Brush.verticalGradient(
                                        colors =
                                            listOf(
                                                MaterialTheme.colorScheme.primaryContainer.copy(
                                                    alpha = 0.8f,
                                                ),
                                                MaterialTheme.colorScheme.primaryContainer.copy(
                                                    alpha = 0.4f,
                                                ),
                                                Color.Transparent,
                                            ),
                                    ),
                            ),
                )

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(AppDimensions.Padding.xl),
                    verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                    ) {
                        QuizDateBadge(
                            date = quizInfo.quizDate,
                            modifier = Modifier.weight(1f, false),
                        )

                        QuizStatusIndicator(
                            isActive = quizInfo.isActive,
                            isExpired = quizInfo.isExpired,
                            hasUserAttempted = quizInfo.hasUserAttempted,
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
                    ) {
                        TitleLargeText(
                            text = quizInfo.title,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )

                        BodyMediumText(
                            text = quizInfo.description,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    QuizQuickStats(
                        totalQuestions = quizInfo.totalQuestions,
                        timeRemaining = quizInfo.timeRemaining,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizDateBadge(
    date: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        shadowElevation = AppDimensions.Elevation.small,
    ) {
        Row(
            modifier =
                Modifier.padding(
                    horizontal = AppDimensions.Padding.medium,
                    vertical = AppDimensions.Padding.small,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.small),
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(AppDimensions.IconSize.small),
            )
            BodySmallText(
                text = "Today • $date",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun QuizStatusIndicator(
    isActive: Boolean,
    isExpired: Boolean,
    hasUserAttempted: Boolean,
    modifier: Modifier = Modifier,
) {
    val (statusText, statusColor, statusIcon) =
        when {
            hasUserAttempted ->
                Triple(
                    "Completed",
                    MaterialTheme.colorScheme.tertiary,
                    Icons.Default.CheckCircle,
                )

            isExpired ->
                Triple(
                    "Expired",
                    MaterialTheme.colorScheme.error,
                    Icons.Default.AccessTime,
                )

            isActive ->
                Triple(
                    "Available",
                    MaterialTheme.colorScheme.primary,
                    Icons.Default.PlayArrow,
                )

            else ->
                Triple(
                    "Pending",
                    MaterialTheme.colorScheme.outline,
                    Icons.Default.Schedule,
                )
        }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        color = statusColor.copy(alpha = 0.15f),
    ) {
        Row(
            modifier =
                Modifier.padding(
                    horizontal = AppDimensions.Padding.medium,
                    vertical = AppDimensions.Padding.small,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.xs),
        ) {
            Icon(
                imageVector = statusIcon,
                contentDescription = null,
                tint = statusColor,
                modifier = Modifier.size(AppDimensions.IconSize.small),
            )
            BodySmallText(
                text = statusText,
                fontWeight = FontWeight.SemiBold,
                color = statusColor,
            )
        }
    }
}

@Composable
private fun QuizQuickStats(
    totalQuestions: Int,
    timeRemaining: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
    ) {
        QuizStatItem(
            icon = Icons.Default.Quiz,
            label = "Questions",
            value = totalQuestions.toString(),
            modifier = Modifier.weight(1f),
        )

        QuizStatItem(
            icon = Icons.Default.Schedule,
            label = "Time Left",
            value = timeRemaining,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun QuizStatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
    ) {
        Row(
            modifier = Modifier.padding(AppDimensions.Padding.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.small),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(AppDimensions.IconSize.medium),
            )
            Column {
                LabelMediumText(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
                BodyMediumText(
                    text = value,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}
