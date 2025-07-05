package com.newton.quiz.presentation.quizinfo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.newton.commonui.components.CivicErrorScreen
import com.newton.commonui.components.CivicLoadingScreen
import com.newton.commonui.theme.backgroundGradient
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent
import com.newton.quiz.presentation.quizinfo.state.QuizInfoUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizInfoScreen(
    uiState: QuizInfoUiState,
    onQuizInfoEvent: (QuizInfoUiEvent) -> Unit,
    onStartQuiz: (String) -> Unit,
    onViewLeaderboard: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(brush = backgroundGradient()),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Daily Civic Quiz",
                        style =
                            MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                    ),
            )

            when {
                uiState.isLoading -> {
                    CivicLoadingScreen(
                        message = "Loading today's civic quiz...",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                uiState.error != null -> {
                    CivicErrorScreen(
                        errorMessage = uiState.error,
                        errorType = uiState.errorType,
                        onRetry = { onQuizInfoEvent(QuizInfoUiEvent.OnRetryLoading) },
                        retryText = "Reload Quiz",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                uiState.quizInfo != null -> {
                    QuizInfoContent(
                        quizInfo = uiState.quizInfo,
                        onQuizInfoEvent = onQuizInfoEvent,
                        onViewLeaderboard = onViewLeaderboard,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}