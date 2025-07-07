package com.newton.quiz.presentation.quizresults.view

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
import com.newton.quiz.presentation.quizresults.event.QuizResultsUiEvent
import com.newton.quiz.presentation.quizresults.state.QuizResultsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizResultsScreen(
    uiState: QuizResultsUiState,
    onQuizResultsEvent: (QuizResultsUiEvent) -> Unit,
    onViewLeaderboard: () -> Unit,
    onRetakeQuiz: () -> Unit,
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
                        text = "Quiz Results",
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
                        message = "Loading your civic quiz results...",
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                uiState.error != null -> {
                    CivicErrorScreen(
                        errorMessage = uiState.error,
                        onRetry = { onQuizResultsEvent(QuizResultsUiEvent.OnRetryResults) },
                        retryText = "Reload Results",
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                uiState.quizSummary != null -> {
                    QuizResultsContent(
                        quizSummary = uiState.quizSummary,
                        onViewLeaderboard = onViewLeaderboard,
                        onRetakeQuiz = onRetakeQuiz,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                else -> {
                    CivicErrorScreen(
                        errorMessage = "No quiz results found for this session",
                        onRetry = { onQuizResultsEvent(QuizResultsUiEvent.OnRetryResults) },
                        retryText = "Try Again",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
