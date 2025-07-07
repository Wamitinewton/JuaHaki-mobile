package com.newton.quiz.presentation.quizinfo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.components.SecondaryButton
import com.newton.domain.models.quiz.QuizInfo
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent

@Composable
fun QuizInfoContent(
    quizInfo: QuizInfo,
    onQuizInfoEvent: (QuizInfoUiEvent) -> Unit,
    onViewLeaderboard: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier =
            modifier
                .verticalScroll(scrollState)
                .padding(16.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Box {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                brush =
                                    Brush.linearGradient(
                                        colors =
                                            listOf(
                                                MaterialTheme.colorScheme.primaryContainer,
                                                MaterialTheme.colorScheme.secondaryContainer,
                                            ),
                                    ),
                            ),
                )

                Column(
                    modifier = Modifier.padding(24.dp),
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                    ) {
                        Text(
                            text = "Today's Quiz • ${quizInfo.quizDate}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = quizInfo.title,
                        style =
                            MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = quizInfo.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Time remaining: ${quizInfo.timeRemaining}",
                            style =
                                MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Medium,
                                ),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (quizInfo.hasUserAttempted && quizInfo.userLastAttempt != null) {
            PreviousAttemptCard(
                attempt = quizInfo.userLastAttempt!!,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (!quizInfo.hasUserAttempted && quizInfo.isActive && !quizInfo.isExpired) {
                PrimaryButton(
                    text = "Start Quiz",
                    onClick = {
                        onQuizInfoEvent(QuizInfoUiEvent.OnStartQuiz)
                    },
                    leadingIcon = Icons.Default.Quiz,
                    modifier = Modifier.fillMaxWidth(),
                )
            } else if (quizInfo.hasUserAttempted) {
                SecondaryButton(
                    text = "View Results",
                    onClick = {
                        onQuizInfoEvent(QuizInfoUiEvent.OnStartQuiz)
                    },
                    leadingIcon = Icons.Default.EmojiEvents,
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                        ),
                ) {
                    Text(
                        text = if (quizInfo.isExpired) "Quiz has expired" else "Quiz is not active",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                    )
                }
            }

            SecondaryButton(
                text = "View Leaderboard",
                onClick = onViewLeaderboard,
                leadingIcon = Icons.Default.EmojiEvents,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
