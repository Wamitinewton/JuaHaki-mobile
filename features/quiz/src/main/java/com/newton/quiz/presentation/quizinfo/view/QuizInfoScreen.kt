@file:OptIn(ExperimentalMaterial3Api::class)

package com.newton.quiz.presentation.quizinfo.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.newton.commonui.theme.backgroundGradient
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent
import com.newton.quiz.presentation.quizinfo.state.QuizInfoUiState
import com.newton.quiz.presentation.quizinfo.view.components.*

@ExperimentalMaterial3Api
@Composable
fun QuizInfoScreen(
    uiState: QuizInfoUiState,
    onQuizInfoEvent: (QuizInfoUiEvent) -> Unit,
    onViewLeaderboard: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.quizInfo) {
        if (uiState.quizInfo != null) {
            contentVisible = true
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = backgroundGradient()),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            QuizInfoTopBar(
                title = uiState.quizInfo?.description ?: "Daily Civic Quiz",
                onNavigateBack = onNavigateBack
            )

            when {
                uiState.isLoading -> {
                    QuizInfoLoadingContent(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                uiState.error != null -> {
                    QuizInfoErrorContent(
                        error = uiState.error,
                        errorType = uiState.errorType,
                        onRetry = { onQuizInfoEvent(QuizInfoUiEvent.OnRetryLoading) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                uiState.quizInfo != null -> {
                    AnimatedVisibility(
                        visible = contentVisible,
                        enter = fadeIn(
                            animationSpec = tween(600, delayMillis = 200)
                        ) + slideInVertically(
                            animationSpec = tween(600, delayMillis = 200),
                            initialOffsetY = { it / 3 }
                        )
                    ) {
                        QuizInfoContent(
                            quizInfo = uiState.quizInfo,
                            onQuizInfoEvent = onQuizInfoEvent,
                            onViewLeaderboard = onViewLeaderboard,
                            scrollState = scrollState,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                else -> {
                    QuizInfoEmptyState(
                        onRetry = { onQuizInfoEvent(QuizInfoUiEvent.OnRetryLoading) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}