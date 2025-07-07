package com.newton.quiz.presentation.quizinfo.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.domain.models.quiz.QuizInfo
import com.newton.domain.models.quiz.UserQuizSummary
import java.util.Locale

@Composable
fun QuizInsightsSection(
    quizInfo: QuizInfo,
    modifier: Modifier = Modifier
) {
    if (!quizInfo.hasUserAttempted || quizInfo.userLastAttempt == null) return

    val userAttempt = quizInfo.userLastAttempt
    val insights = userAttempt?.let { generateInsights(it) }

    if (insights.isNullOrEmpty()) return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Quick Insights",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        insights.forEach { insight ->
            InsightCard(insight = insight)
        }
    }
}

@Composable
private fun InsightCard(
    insight: QuizInsight,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = insight.containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
            ) {
                Icon(
                    imageVector = insight.icon,
                    contentDescription = null,
                    tint = insight.iconColor,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = insight.title,
                    style = MaterialTheme.typography.labelMedium,
                    color = insight.contentColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = insight.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = insight.contentColor.copy(alpha = 0.8f)
                )
            }
        }
    }
}

private data class QuizInsight(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconColor: androidx.compose.ui.graphics.Color,
    val containerColor: androidx.compose.ui.graphics.Color,
    val contentColor: androidx.compose.ui.graphics.Color
)

@Composable
private fun generateInsights(userAttempt: UserQuizSummary): List<QuizInsight> {
    val insights = mutableListOf<QuizInsight>()

    val avgTimePerQuestion = userAttempt.durationSeconds?.div(userAttempt.totalQuestions.toDouble()) ?: 0.0
    when {
        avgTimePerQuestion < 20 -> {
            insights.add(
                QuizInsight(
                    title = "Speed Demon! ⚡",
                    description = "You completed this quiz quickly with ${String.format(Locale.US ,"%.1f", avgTimePerQuestion)}s per question on average",
                    icon = Icons.Default.Speed,
                    iconColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
        avgTimePerQuestion > 60 -> {
            insights.add(
                QuizInsight(
                    title = "Thoughtful Approach",
                    description = "You took your time with ${String.format(Locale.US,"%.1f", avgTimePerQuestion)}s per question - great for accuracy!",
                    icon = Icons.Default.Psychology,
                    iconColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }

    when (userAttempt.score) {
        in 90..100 -> {
            insights.add(
                QuizInsight(
                    title = "Outstanding Performance! 🏆",
                    description = "You're in the top tier with ${userAttempt.score}% - civic knowledge expert!",
                    icon = Icons.Default.EmojiEvents,
                    iconColor = MaterialTheme.colorScheme.tertiary,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            )
        }
        in 70..89 -> {
            insights.add(
                QuizInsight(
                    title = "Great Job! 👍",
                    description = "Solid performance with ${userAttempt.score}% - you know your civic duties well",
                    icon = Icons.Default.CheckCircle,
                    iconColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }

    val categoryStats = userAttempt.categoryPerformance.categoryStats
    val perfectCategories = categoryStats.values.filter { it.percentage == 100.0 }

    if (perfectCategories.isNotEmpty()) {
        insights.add(
            QuizInsight(
                title = "Perfect Categories! 🎯",
                description = "100% accuracy in ${perfectCategories.joinToString(", ") { it.category }}",
                icon = Icons.Default.Stars,
                iconColor = MaterialTheme.colorScheme.tertiary,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        )
    }

    return insights
}