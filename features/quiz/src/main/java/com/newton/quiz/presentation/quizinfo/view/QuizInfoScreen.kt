package com.newton.quiz.presentation.quizinfo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.commonui.theme.backgroundGradient
import com.newton.domain.models.quiz.QuizInfo
import com.newton.quiz.presentation.quizinfo.state.QuizInfoUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizInfoScreen(
    onStartQuiz: (String) -> Unit = {},
    onViewLeaderboard: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val uiState by remember {
        mutableStateOf(
            QuizInfoUiState(
                isLoading = false,
                quizInfo =
                    QuizInfo(
                        quizId = 1L,
                        quizDate = "2024-03-15",
                        title = "Constitutional Rights & Freedoms",
                        description = "Test your knowledge of fundamental rights and freedoms as outlined in the Kenyan Constitution",
                        totalQuestions = 10,
                        isActive = true,
                        isExpired = false,
                        expiresAt = "2024-03-16T00:00:00",
                        hasUserAttempted = false,
                        timeRemaining = "18h 45m",
                    ),
            ),
        )
    }

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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Error loading quiz",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uiState.error!!,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                else -> {
                    QuizInfoContent(
                        quizInfo = uiState.quizInfo!!,
                        onStartQuiz = onStartQuiz,
                        onViewLeaderboard = onViewLeaderboard,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
