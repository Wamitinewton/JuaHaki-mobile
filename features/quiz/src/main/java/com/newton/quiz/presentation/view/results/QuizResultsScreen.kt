package com.newton.quiz.presentation.view.results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.commonui.theme.backgroundGradient
import com.newton.domain.models.quiz.CategoryPerformance
import com.newton.domain.models.quiz.CategoryStats
import com.newton.domain.models.quiz.QuestionResult
import com.newton.domain.models.quiz.UserQuizSummary
import com.newton.quiz.presentation.state.QuizResultsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizResultsScreen(
    sessionId: String,
    onViewLeaderboard: () -> Unit = {},
    onRetakeQuiz: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    // Mock data - Replace with ViewModel state
    var uiState by remember {
        mutableStateOf(
            QuizResultsUiState(
                isLoading = false,
                quizSummary =
                    UserQuizSummary(
                        sessionId = sessionId,
                        attemptId = 1L,
                        quizTitle = "Constitutional Rights & Freedoms",
                        totalQuestions = 10,
                        questionsAnswered = 10,
                        correctAnswers = 7,
                        score = 70,
                        performanceLevel = "Good",
                        startedAt = "2024-03-15T10:00:00",
                        completedAt = "2024-03-15T10:15:30",
                        durationSeconds = 930,
                        durationFormatted = "15m 30s",
                        questionResults =
                            listOf(
                                QuestionResult(
                                    questionNumber = 1,
                                    questionText = "According to Article 27 of the Constitution...",
                                    category = "Bill of Rights",
                                    selectedAnswer = "D",
                                    correctAnswer = "D",
                                    selectedOptionText = "Economic status or employment",
                                    correctOptionText = "Economic status or employment",
                                    isCorrect = true,
                                    explanation = "Article 27 prohibits discrimination...",
                                    timeSpentSeconds = 45,
                                ),
                                QuestionResult(
                                    questionNumber = 2,
                                    questionText = "Which chapter of the Constitution...",
                                    category = "Government Structure",
                                    selectedAnswer = "B",
                                    correctAnswer = "C",
                                    selectedOptionText = "Chapter 9",
                                    correctOptionText = "Chapter 10",
                                    isCorrect = false,
                                    explanation = "Chapter 10 deals with the judiciary...",
                                    timeSpentSeconds = 60,
                                ),
                            ),
                        categoryPerformance =
                            CategoryPerformance(
                                categoryStats =
                                    mapOf(
                                        "Bill of Rights" to
                                            CategoryStats(
                                                category = "Bill of Rights",
                                                totalQuestions = 4,
                                                correctAnswers = 3,
                                                percentage = 75.0,
                                                performance = "Good",
                                                feedback = "Strong understanding of constitutional rights",
                                            ),
                                        "Government Structure" to
                                            CategoryStats(
                                                category = "Government Structure",
                                                totalQuestions = 6,
                                                correctAnswers = 4,
                                                percentage = 67.0,
                                                performance = "Fair",
                                                feedback = "Consider reviewing government structure",
                                            ),
                                    ),
                                strongestCategory = "Bill of Rights",
                                weakestCategory = "Government Structure",
                                overallFeedback = "Good performance overall with room for improvement",
                            ),
                        completionMessage = "Good job! You have strong civic knowledge! 👍",
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
                            imageVector = Icons.Default.ArrowBack,
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
                                text = "Error loading results",
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
                    QuizResultsContent(
                        quizSummary = uiState.quizSummary!!,
                        onViewLeaderboard = onViewLeaderboard,
                        onRetakeQuiz = onRetakeQuiz,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
