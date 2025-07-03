package com.newton.quiz.presentation.view.quizgame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.newton.domain.models.quiz.AnswerResult
import com.newton.domain.models.quiz.QuizOption
import com.newton.domain.models.quiz.QuizQuestion
import com.newton.domain.models.quiz.QuizSession
import com.newton.quiz.presentation.state.QuizGameUiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizGameScreen(
    sessionId: String,
    onQuizComplete: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var uiState by remember {
        mutableStateOf(
            QuizGameUiState(
                isLoading = false,
                currentSession = QuizSession(
                    sessionId = sessionId,
                    quizId = 1L,
                    title = "Constitutional Rights & Freedoms",
                    totalQuestions = 10,
                    currentQuestion = QuizQuestion(
                        questionId = 1L,
                        questionNumber = 1,
                        questionText = "According to Article 27 of the Constitution of Kenya 2010, which of the following is NOT a ground for discrimination?",
                        category = "Bill of Rights",
                        difficulty = "Medium",
                        options = listOf(
                            QuizOption("A", "Race, color, or ethnic origin"),
                            QuizOption("B", "Political opinion or belief"),
                            QuizOption("C", "Physical ability or disability"),
                            QuizOption("D", "Economic status or employment")
                        ),
                        sourceReference = "Article 27, Constitution of Kenya 2010"
                    )
                ),
                currentQuestionNumber = 1,
                score = 0,
                timeSpent = 0
            )
        )
    }

    var showExitDialog by remember { mutableStateOf(false) }

    // Timer effect
    LaunchedEffect(uiState.currentSession) {
        if (uiState.currentSession != null && !uiState.showExplanation) {
            while (true) {
                delay(1000)
                uiState = uiState.copy(timeSpent = uiState.timeSpent + 1)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = backgroundGradient())
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.currentSession?.title ?: "Quiz",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { showExitDialog = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Exit Quiz"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                )
            )

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uiState.error!!,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                else -> {
                    QuizGameContent(
                        uiState = uiState,
                        onAnswerSelected = { answer ->
                            uiState = uiState.copy(currentAnswer = answer)
                        },
                        onSubmitAnswer = {
                            val isCorrect = uiState.currentAnswer == "D"
                            val result = AnswerResult(
                                correct = isCorrect,
                                message = if (isCorrect) "Correct!" else "Incorrect",
                                correctAnswer = "D",
                                correctOptionText = "Economic status or employment",
                                explanation = "Article 27 of the Constitution prohibits discrimination on various grounds including race, gender, pregnancy, marital status, health status, ethnic or social origin, color, age, disability, religion, conscience, belief, culture, dress, language or birth. However, economic status or employment is not explicitly listed as a protected ground.",
                                currentScore = if (isCorrect) uiState.score + 10 else uiState.score,
                                questionsAnswered = uiState.currentQuestionNumber,
                                totalQuestions = uiState.currentSession?.totalQuestions ?: 10,
                                hasNextQuestion = uiState.currentQuestionNumber < (uiState.currentSession?.totalQuestions ?: 10)
                            )

                            uiState = uiState.copy(
                                showExplanation = true,
                                answerResult = result,
                                score = result.currentScore
                            )
                        },
                        onNextQuestion = {
                            if (uiState.currentQuestionNumber < (uiState.currentSession?.totalQuestions ?: 10)) {
                                uiState = uiState.copy(
                                    currentQuestionNumber = uiState.currentQuestionNumber + 1,
                                    currentAnswer = "",
                                    showExplanation = false,
                                    answerResult = null,
                                    timeSpent = 0
                                )
                            } else {
                                onQuizComplete(sessionId)
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                title = {
                    Text(
                        text = "Exit Quiz?",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                text = {
                    Text("Are you sure you want to exit? Your progress will be lost.")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showExitDialog = false
                            onNavigateBack()
                        }
                    ) {
                        Text("Exit")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showExitDialog = false }
                    ) {
                        Text("Continue Quiz")
                    }
                }
            )
        }
    }
}
