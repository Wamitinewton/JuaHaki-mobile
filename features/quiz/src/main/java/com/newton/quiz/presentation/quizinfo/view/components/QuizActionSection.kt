package com.newton.quiz.presentation.quizinfo.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.newton.commonui.components.LabelMediumText
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.components.SecondaryButton
import com.newton.commonui.components.TitleLargeText
import com.newton.commonui.theme.AppDimensions
import com.newton.domain.models.quiz.QuizInfo
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent

@Composable
fun QuizActionsSection(
    quizInfo: QuizInfo,
    onQuizInfoEvent: (QuizInfoUiEvent) -> Unit,
    onViewLeaderboard: () -> Unit,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(600, delayMillis = 300, easing = FastOutSlowInEasing)
        ) + slideInVertically(
            animationSpec = tween(600, delayMillis = 300, easing = FastOutSlowInEasing),
            initialOffsetY = { it / 3 }
        )
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large)
        ) {
            PrimaryActionCard(
                quizInfo = quizInfo,
                onQuizInfoEvent = onQuizInfoEvent,
                modifier = Modifier.fillMaxWidth()
            )

            SecondaryActionsRow(
                quizInfo = quizInfo,
                onViewLeaderboard = onViewLeaderboard,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PrimaryActionCard(
    quizInfo: QuizInfo,
    onQuizInfoEvent: (QuizInfoUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        colors = CardDefaults.cardColors(
            containerColor = when {
                quizInfo.hasUserAttempted -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
                quizInfo.isActive && !quizInfo.isExpired -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.medium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimensions.Padding.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large)
        ) {
            PrimaryActionHeader(quizInfo = quizInfo)

            when {
                !quizInfo.hasUserAttempted && quizInfo.isActive && !quizInfo.isExpired -> {
                    PrimaryButton(
                        text = "Start Quiz",
                        onClick = { onQuizInfoEvent(QuizInfoUiEvent.OnStartQuiz) },
                        trailingIcon = Icons.Default.PlayArrow,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                quizInfo.hasUserAttempted -> {
                    PrimaryButton(
                        text = "View Detailed Results",
                        onClick = { onQuizInfoEvent(QuizInfoUiEvent.OnStartQuiz) },
                        trailingIcon = Icons.Default.Analytics,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                quizInfo.isExpired -> {
                    Surface(
                        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = "Quiz Expired",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(
                                horizontal = AppDimensions.Padding.xl,
                                vertical = AppDimensions.Padding.large
                            )
                        )
                    }
                }

                else -> {
                    Surface(
                        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = "Quiz Not Available",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(
                                horizontal = AppDimensions.Padding.xl,
                                vertical = AppDimensions.Padding.large
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PrimaryActionHeader(
    quizInfo: QuizInfo,
    modifier: Modifier = Modifier
) {
    val (icon, title, subtitle, iconColor) = when {
        quizInfo.hasUserAttempted -> Quadruple(
            Icons.Default.Analytics,
            "Review Your Performance",
            "See detailed breakdown and insights",
            MaterialTheme.colorScheme.tertiary
        )
        quizInfo.isActive && !quizInfo.isExpired -> Quadruple(
            Icons.Default.PlayArrow,
            "Ready to Test Your Knowledge?",
            "Answer ${quizInfo.totalQuestions} questions about civic rights",
            MaterialTheme.colorScheme.primary
        )
        quizInfo.isExpired -> Quadruple(
            Icons.Default.AccessTime,
            "Quiz Time Expired",
            "This quiz is no longer available",
            MaterialTheme.colorScheme.outline
        )
        else -> Quadruple(
            Icons.Default.Schedule,
            "Quiz Coming Soon",
            "Check back when it becomes available",
            MaterialTheme.colorScheme.outline
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium)
    ) {
        Surface(
            shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
            color = iconColor.copy(alpha = 0.15f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
                    .padding(AppDimensions.Padding.large)
                    .size(AppDimensions.IconSize.xl)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.xs)
        ) {
            TitleLargeText(
                text = title,
                color = MaterialTheme.colorScheme.onSurface
            )
            LabelMediumText(
                text = subtitle,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun SecondaryActionsRow(
    quizInfo: QuizInfo,
    onViewLeaderboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium)
    ) {
        SecondaryButton(
            text = "Leaderboard",
            onClick = onViewLeaderboard,
            leadingIcon = Icons.Default.EmojiEvents,
            modifier = Modifier.weight(1f)
        )

        if (quizInfo.hasUserAttempted) {
            SecondaryButton(
                text = "Share Score",
                onClick = { },
                leadingIcon = Icons.Default.Share,
                modifier = Modifier.weight(1f)
            )
        } else {
            SecondaryButton(
                text = "History",
                onClick = {  },
                leadingIcon = Icons.Default.History,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)