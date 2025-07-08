package com.newton.quiz.presentation.quizresults.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.domain.models.quiz.UserQuizSummary
import com.newton.quiz.presentation.components.QuizStatsCard

@Composable
fun QuickStatsRow(
    quizSummary: UserQuizSummary,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        QuizStatsCard(
            title = "Correct",
            value = "${quizSummary.correctAnswers}/${quizSummary.totalQuestions}",
            subtitle = "Answers",
            icon = Icons.Default.CheckCircle,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f),
        )

        QuizStatsCard(
            title = "Duration",
            value = quizSummary.durationFormatted,
            subtitle = "Total Time",
            icon = Icons.Default.Timer,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f),
        )

        QuizStatsCard(
            title = "Rank",
            value = "#42",
            subtitle = "Today",
            icon = Icons.Default.EmojiEvents,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f),
        )
    }
}
