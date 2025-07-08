package com.newton.quiz.presentation.quizgame.view

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
import com.newton.quiz.presentation.quizgame.event.QuizGameUiEvent
import com.newton.quiz.presentation.quizgame.state.QuizGameUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizGameScreen(
    uiState: QuizGameUiState,
    onQuizGameEvent: (QuizGameUiEvent) -> Unit,
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
                        text = uiState.currentSession?.title ?: "Quiz",
                        style =
                            MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Exit Quiz",
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
                        message = "Loading ....",
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                uiState.error != null -> {
                    CivicErrorScreen(
                        errorMessage = uiState.error,
                        onRetry = { onQuizGameEvent(QuizGameUiEvent.OnClearError) },
                        retryText = "Continue Quiz",
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                uiState.currentSession?.currentQuestion != null -> {
                    QuizGameContent(
                        uiState = uiState,
                        onAnswerSelected = { answer ->
                            onQuizGameEvent(QuizGameUiEvent.OnSelectAnswer(answer))
                        },
                        onSubmitAnswer = {
                            onQuizGameEvent(QuizGameUiEvent.OnSubmitAnswer)
                        },
                        onNextQuestion = {
                            onQuizGameEvent(QuizGameUiEvent.OnNextQuestion)
                        },
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                else -> {
                    CivicErrorScreen(
                        errorMessage = "No quiz session found",
                        onRetry = null,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
