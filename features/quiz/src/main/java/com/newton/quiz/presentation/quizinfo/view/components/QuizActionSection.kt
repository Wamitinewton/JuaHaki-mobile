package com.newton.quiz.presentation.quizinfo.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.components.SecondaryButton
import com.newton.domain.models.quiz.QuizInfo
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent

@Composable
fun QuizActionsSection(
    quizInfo: QuizInfo,
    onQuizInfoEvent: (QuizInfoUiEvent) -> Unit,
    onViewLeaderboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when {
            !quizInfo.hasUserAttempted && quizInfo.isActive && !quizInfo.isExpired -> {
                PrimaryButton(
                    text = "Start Quiz",
                    trailingIcon = Icons.Default.PlayArrow,
                    onClick = { onQuizInfoEvent(QuizInfoUiEvent.OnStartQuiz) },
                )
            }

            quizInfo.hasUserAttempted -> {
                PrimaryButton(
                    text = "View Results",
                    trailingIcon = Icons.Default.Analytics,
                    onClick = { onQuizInfoEvent(QuizInfoUiEvent.OnStartQuiz) },
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SecondaryButton(
                text = "Leaderboard",
                trailingIcon = Icons.Default.EmojiEvents,
                onClick = onViewLeaderboard,
                modifier = Modifier.weight(1f)
            )

            if (quizInfo.hasUserAttempted) {
                SecondaryButton(
                    text = "Share Score",
                    trailingIcon = Icons.Default.Share,
                    onClick = {  },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
