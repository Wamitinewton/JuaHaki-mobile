package com.newton.quiz.presentation.quizinfo.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.newton.commonui.theme.AppDimensions
import com.newton.core.utils.DateUtils.toQuizDate
import com.newton.domain.models.quiz.QuizInfo

@Composable
fun QuizStatusSection(
    quizInfo: QuizInfo,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter =
            fadeIn(
                animationSpec = tween(600, delayMillis = 100, easing = FastOutSlowInEasing),
            ) +
                slideInVertically(
                    animationSpec = tween(600, delayMillis = 100, easing = FastOutSlowInEasing),
                    initialOffsetY = { it / 3 },
                ),
    ) {
        when {
            quizInfo.hasUserAttempted && quizInfo.userLastAttempt != null -> {
                CompletedQuizStatusCard(
                    score = quizInfo.userLastAttempt?.score ?: 0,
                    performanceLevel = quizInfo.userLastAttempt?.performanceLevel ?: "",
                    completedAt = quizInfo.userLastAttempt?.completedAt?.toQuizDate() ?: "",
                    duration = quizInfo.userLastAttempt?.durationFormatted ?: "",
                    completionMessage = quizInfo.userLastAttempt?.completionMessage ?: "",
                    modifier = modifier,
                )
            }

            quizInfo.isActive && !quizInfo.isExpired -> {
                ActiveQuizStatusCard(
                    timeRemaining = quizInfo.timeRemaining,
                    totalQuestions = quizInfo.totalQuestions,
                    modifier = modifier,
                )
            }

            else -> {
                InactiveQuizStatusCard(
                    isExpired = quizInfo.isExpired,
                    expiresAt = quizInfo.expiresAt,
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
private fun CompletedQuizStatusCard(
    score: Int,
    performanceLevel: String,
    completedAt: String,
    duration: String,
    completionMessage: String,
    modifier: Modifier = Modifier,
) {
    val scoreColor =
        when {
            score >= 80 -> Color(0xFF10B981)
            score >= 60 -> Color(0xFFF59E0B)
            else -> Color(0xFFEF4444)
        }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        colors =
            CardDefaults.cardColors(
                containerColor = scoreColor.copy(alpha = 0.08f),
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.medium),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.Padding.xl),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
            ) {
                Surface(
                    shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
                    color = scoreColor.copy(alpha = 0.15f),
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = scoreColor,
                        modifier =
                            Modifier
                                .padding(AppDimensions.Padding.medium)
                                .size(AppDimensions.IconSize.large),
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Quiz Completed!",
                        style =
                            MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "Completed on $completedAt",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
            ) {
                ScoreMetricCard(
                    label = "Your Score",
                    value = "$score%",
                    color = scoreColor,
                    modifier = Modifier.weight(1f),
                )

                if (duration.isNotEmpty()) {
                    ScoreMetricCard(
                        label = "Duration",
                        value = duration,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            if (performanceLevel.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
                    color = scoreColor.copy(alpha = 0.15f),
                ) {
                    Text(
                        text = performanceLevel,
                        style =
                            MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                            ),
                        color = scoreColor,
                        modifier =
                            Modifier.padding(
                                horizontal = AppDimensions.Padding.large,
                                vertical = AppDimensions.Padding.medium,
                            ),
                        textAlign = TextAlign.Center,
                    )
                }
            }

            if (completionMessage.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                ) {
                    Text(
                        text = completionMessage,
                        style =
                            MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 20.sp,
                            ),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        modifier = Modifier.padding(AppDimensions.Padding.large),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
private fun ActiveQuizStatusCard(
    timeRemaining: String,
    totalQuestions: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.medium),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.Padding.xl),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
        ) {
            Surface(
                shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier =
                        Modifier
                            .padding(AppDimensions.Padding.medium)
                            .size(AppDimensions.IconSize.xl),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Quiz Available",
                    style =
                        MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(AppDimensions.Spacing.xs))

                Text(
                    text = "$totalQuestions questions • $timeRemaining remaining",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
            }

            Surface(
                shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier =
                        Modifier
                            .padding(AppDimensions.Padding.small)
                            .size(AppDimensions.IconSize.medium),
                )
            }
        }
    }
}

@Composable
private fun InactiveQuizStatusCard(
    isExpired: Boolean,
    expiresAt: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.small),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.Padding.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
        ) {
            Surface(
                shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
            ) {
                Icon(
                    imageVector = if (isExpired) Icons.Default.AccessTime else Icons.Default.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier =
                        Modifier
                            .padding(AppDimensions.Padding.medium)
                            .size(AppDimensions.IconSize.xl),
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.xs),
            ) {
                Text(
                    text = if (isExpired) "Quiz Expired" else "Quiz Not Active",
                    style =
                        MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )

                if (expiresAt.isNotEmpty()) {
                    Text(
                        text = if (isExpired) "Expired on $expiresAt" else "Available until $expiresAt",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
private fun ScoreMetricCard(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
        color = color.copy(alpha = 0.1f),
    ) {
        Column(
            modifier = Modifier.padding(AppDimensions.Padding.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.xs),
        ) {
            Text(
                text = value,
                style =
                    MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                color = color,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
            )
        }
    }
}
