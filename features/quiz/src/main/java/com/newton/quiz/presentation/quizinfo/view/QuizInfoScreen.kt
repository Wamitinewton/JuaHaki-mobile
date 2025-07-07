package com.newton.quiz.presentation.quizinfo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import com.newton.commonui.components.CivicErrorScreen
import com.newton.commonui.components.CivicLoadingScreen
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent
import com.newton.quiz.presentation.quizinfo.state.QuizInfoUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizInfoScreen(
    uiState: QuizInfoUiState,
    onQuizInfoEvent: (QuizInfoUiEvent) -> Unit,
    onViewLeaderboard: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.3f),
            MaterialTheme.colorScheme.surface
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = backgroundGradient),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Daily Civic Quiz",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                ),
                windowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
            )

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CivicLoadingScreen(
                            message = "Loading today's civic quiz...",
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CivicErrorScreen(
                            errorMessage = uiState.error,
                            errorType = uiState.errorType,
                            onRetry = { onQuizInfoEvent(QuizInfoUiEvent.OnRetryLoading) },
                            retryText = "Reload Quiz",
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
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