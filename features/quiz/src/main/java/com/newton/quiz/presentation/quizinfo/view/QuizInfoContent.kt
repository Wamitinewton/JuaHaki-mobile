package com.newton.quiz.presentation.quizinfo.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.domain.models.quiz.QuizInfo
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent
import com.newton.quiz.presentation.quizinfo.view.components.*

@Composable
fun QuizInfoContent(
    quizInfo: QuizInfo,
    onQuizInfoEvent: (QuizInfoUiEvent) -> Unit,
    onViewLeaderboard: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QuizHeaderSection(
            quizInfo = quizInfo
        )

        QuizStatusSection(
            quizInfo = quizInfo
        )

        if (quizInfo.hasUserAttempted && quizInfo.userLastAttempt != null) {
            QuizInsightsSection(
                quizInfo = quizInfo
            )
        }
        QuizActionsSection(
            quizInfo = quizInfo,
            onQuizInfoEvent = onQuizInfoEvent,
            onViewLeaderboard = onViewLeaderboard
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}