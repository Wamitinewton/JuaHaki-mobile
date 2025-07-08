package com.newton.quiz.presentation.quizresults.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.components.SecondaryButton
import com.newton.domain.models.quiz.UserQuizSummary

@Composable
fun QuizResultsContent(
    quizSummary: UserQuizSummary,
    onViewLeaderboard: () -> Unit,
    onRetakeQuiz: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        item {
            ScoreHeaderCard(
                score = quizSummary.score,
                performanceLevel = quizSummary.performanceLevel,
                completionMessage = quizSummary.completionMessage,
            )
        }

        item {
            QuickStatsRow(quizSummary = quizSummary)
        }

        item {
            CategoryPerformanceCard(
                categoryPerformance = quizSummary.categoryPerformance,
            )
        }

        item {
            Text(
                text = "Question Details",
                style =
                    MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 4.dp),
            )
        }

        items(quizSummary.questionResults) { questionResult ->
            QuestionResultCard(questionResult = questionResult)
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    SecondaryButton(
                        text = "Leaderboard",
                        onClick = onViewLeaderboard,
                        leadingIcon = Icons.Default.EmojiEvents,
                        modifier = Modifier.weight(1f),
                    )

                    SecondaryButton(
                        text = "Share",
                        onClick = { /* TODO: Implement sharing */ },
                        leadingIcon = Icons.Default.Share,
                        modifier = Modifier.weight(1f),
                    )
                }

                PrimaryButton(
                    text = "Take Another Quiz",
                    onClick = onRetakeQuiz,
                    leadingIcon = Icons.Default.Refresh,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
