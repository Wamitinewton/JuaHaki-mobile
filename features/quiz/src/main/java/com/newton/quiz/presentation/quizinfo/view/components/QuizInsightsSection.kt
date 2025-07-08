package com.newton.quiz.presentation.quizinfo.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.commonui.theme.AppDimensions
import com.newton.domain.models.quiz.UserQuizSummary
import java.util.Locale

@Composable
fun QuizInsightsSection(
    userAttempt: UserQuizSummary,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    val insights = generateInsights(userAttempt)

    AnimatedVisibility(
        visible = isVisible,
        enter =
            fadeIn(
                animationSpec = tween(600, delayMillis = 200, easing = FastOutSlowInEasing),
            ) +
                    slideInVertically(
                        animationSpec = tween(600, delayMillis = 200, easing = FastOutSlowInEasing),
                        initialOffsetY = { it / 3 },
                    ),
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.small),
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(AppDimensions.IconSize.medium),
                )
                Text(
                    text = "Performance Insights",
                    style =
                        MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            if (insights.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppDimensions.Padding.xs),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
                ) {
                    items(insights) { insight ->
                        InsightCard(
                            insight = insight,
                            modifier = Modifier.width(280.dp),
                        )
                    }
                }
            }

            CategoryPerformanceOverview(
                categoryPerformance = userAttempt.categoryPerformance,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun InsightCard(
    insight: QuizInsight,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        colors =
            CardDefaults.cardColors(
                containerColor = insight.containerColor,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.medium),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.Padding.large),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
            ) {
                Surface(
                    shape = RoundedCornerShape(AppDimensions.CornerRadius.small),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                ) {
                    Icon(
                        imageVector = insight.icon,
                        contentDescription = null,
                        tint = insight.iconColor,
                        modifier =
                            Modifier
                                .padding(AppDimensions.Padding.small)
                                .size(AppDimensions.IconSize.medium),
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = insight.title,
                        style =
                            MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        color = insight.contentColor,
                    )
                    Spacer(modifier = Modifier.height(AppDimensions.Spacing.xs))
                    Text(
                        text = insight.description,
                        style =
                            MaterialTheme.typography.bodySmall.copy(
                                lineHeight = 16.sp,
                            ),
                        color = insight.contentColor.copy(alpha = 0.8f),
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryPerformanceOverview(
    categoryPerformance: com.newton.domain.models.quiz.CategoryPerformance,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.small),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.Padding.large),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
        ) {
            Text(
                text = "Category Breakdown",
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
            )

            categoryPerformance.categoryStats.values.take(3).forEach { categoryStats ->
                CategoryProgressItem(
                    categoryStats = categoryStats,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            if (categoryPerformance.categoryStats.size > 3) {
                Surface(
                    shape = RoundedCornerShape(AppDimensions.CornerRadius.small),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                ) {
                    Text(
                        text = "+${categoryPerformance.categoryStats.size - 3} more categories",
                        style =
                            MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium,
                            ),
                        color = MaterialTheme.colorScheme.primary,
                        modifier =
                            Modifier.padding(
                                horizontal = AppDimensions.Padding.medium,
                                vertical = AppDimensions.Padding.small,
                            ),
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryProgressItem(
    categoryStats: com.newton.domain.models.quiz.CategoryStats,
    modifier: Modifier = Modifier,
) {
    val progressColor =
        when {
            categoryStats.percentage >= 80 -> Color(0xFF10B981)
            categoryStats.percentage >= 60 -> Color(0xFFF59E0B)
            else -> Color(0xFFEF4444)
        }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.xs),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = categoryStats.category,
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )

            Surface(
                shape = RoundedCornerShape(AppDimensions.CornerRadius.small),
                color = progressColor.copy(alpha = 0.15f),
            ) {
                Text(
                    text = "${categoryStats.correctAnswers}/${categoryStats.totalQuestions}",
                    style =
                        MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = progressColor,
                    modifier =
                        Modifier.padding(
                            horizontal = AppDimensions.Padding.small,
                            vertical = AppDimensions.Padding.xs,
                        ),
                )
            }
        }

        LinearProgressIndicator(
            progress = { (categoryStats.percentage / 100).toFloat() },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(6.dp),
            color = progressColor,
            trackColor = progressColor.copy(alpha = 0.2f),
        )
    }
}

private data class QuizInsight(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconColor: Color,
    val containerColor: Color,
    val contentColor: Color,
)

@Composable
private fun generateInsights(userAttempt: UserQuizSummary): List<QuizInsight> {
    val insights = mutableListOf<QuizInsight>()

    val avgTimePerQuestion =
        userAttempt.durationSeconds?.div(userAttempt.totalQuestions.toDouble()) ?: 0.0
    when {
        avgTimePerQuestion < 20 -> {
            insights.add(
                QuizInsight(
                    title = "Speed Champion! ⚡",
                    description = "You completed this quiz quickly with ${
                        String.format(
                            Locale.US,
                            "%.1f",
                            avgTimePerQuestion,
                        )
                    }s per question on average. Great efficiency!",
                    icon = Icons.Default.Speed,
                    iconColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        }

        avgTimePerQuestion > 60 -> {
            insights.add(
                QuizInsight(
                    title = "Thoughtful Approach",
                    description = "You took your time with ${
                        String.format(
                            Locale.US,
                            "%.1f",
                            avgTimePerQuestion,
                        )
                    }s per question. Careful consideration often leads to better accuracy!",
                    icon = Icons.Default.Psychology,
                    iconColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
            )
        }
    }

    // Score performance insight
    when (userAttempt.score) {
        in 90..100 -> {
            insights.add(
                QuizInsight(
                    title = "Outstanding! 🏆",
                    description = "You're in the top tier with ${userAttempt.score}% - you're a true civic knowledge expert!",
                    icon = Icons.Default.EmojiEvents,
                    iconColor = MaterialTheme.colorScheme.tertiary,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f),
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                ),
            )
        }

        in 70..89 -> {
            insights.add(
                QuizInsight(
                    title = "Great Performance! 👍",
                    description = "Solid performance with ${userAttempt.score}% - you have a strong understanding of civic duties.",
                    icon = Icons.Default.CheckCircle,
                    iconColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
            )
        }

        in 50..69 -> {
            insights.add(
                QuizInsight(
                    title = "Room to Grow 📚",
                    description = "You scored ${userAttempt.score}% - a good foundation! Review the areas you missed to strengthen your knowledge.",
                    icon = Icons.Default.School,
                    iconColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        }
    }

    val perfectCategories =
        userAttempt.categoryPerformance.categoryStats.values
            .filter { it.percentage == 100.0 }
    if (perfectCategories.isNotEmpty()) {
        insights.add(
            QuizInsight(
                title = "Perfect Categories! 🎯",
                description = "100% accuracy in ${
                    perfectCategories.take(
                        2,
                    ).joinToString(", ") { it.category }
                }${if (perfectCategories.size > 2) " and more" else ""}",
                icon = Icons.Default.Stars,
                iconColor = MaterialTheme.colorScheme.tertiary,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f),
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            ),
        )
    }

    return insights
}
