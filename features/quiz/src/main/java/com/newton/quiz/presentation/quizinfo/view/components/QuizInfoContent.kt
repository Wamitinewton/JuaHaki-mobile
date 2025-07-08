package com.newton.quiz.presentation.quizinfo.view.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.newton.commonui.theme.AppDimensions
import com.newton.domain.models.quiz.QuizInfo
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent
import kotlinx.coroutines.delay

@Composable
fun QuizInfoContent(
    quizInfo: QuizInfo,
    onQuizInfoEvent: (QuizInfoUiEvent) -> Unit,
    onViewLeaderboard: () -> Unit,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
    var heroVisible by remember { mutableStateOf(false) }
    var statusVisible by remember { mutableStateOf(false) }
    var actionsVisible by remember { mutableStateOf(false) }
    var insightsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        heroVisible = true
        delay(200)
        statusVisible = true
        delay(200)
        actionsVisible = true
        delay(200)
        if (quizInfo.hasUserAttempted && quizInfo.userLastAttempt != null) {
            insightsVisible = true
        }
    }

    Column(
        modifier =
            modifier
                .verticalScroll(scrollState)
                .padding(horizontal = AppDimensions.Padding.screen)
                .padding(bottom = AppDimensions.Padding.xxl),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
    ) {
        Spacer(modifier = Modifier.height(AppDimensions.Spacing.medium))

        QuizHeroSection(
            quizInfo = quizInfo,
            isVisible = heroVisible,
            modifier = Modifier.fillMaxWidth(),
        )

        QuizStatusSection(
            quizInfo = quizInfo,
            isVisible = statusVisible,
            modifier = Modifier.fillMaxWidth(),
        )

        if (quizInfo.hasUserAttempted && quizInfo.userLastAttempt != null) {
            QuizInsightsSection(
                userAttempt = quizInfo.userLastAttempt!!,
                isVisible = insightsVisible,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        QuizActionsSection(
            quizInfo = quizInfo,
            onQuizInfoEvent = onQuizInfoEvent,
            onViewLeaderboard = onViewLeaderboard,
            isVisible = actionsVisible,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppDimensions.Spacing.large))
    }
}
