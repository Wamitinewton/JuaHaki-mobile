package com.newton.quiz.presentation.quizgame.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.newton.commonui.components.BodyMediumText
import com.newton.commonui.components.LabelMediumText
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.components.TitleMediumText
import com.newton.commonui.theme.AppDimensions
import com.newton.quiz.presentation.components.QuizProgressBar
import com.newton.quiz.presentation.components.QuizQuestionCard
import com.newton.quiz.presentation.quizgame.state.QuizGameUiState
import java.util.Locale

@Composable
fun QuizGameContent(
    uiState: QuizGameUiState,
    onAnswerSelected: (String) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val currentQuestion = uiState.currentSession?.currentQuestion

    Column(
        modifier =
            modifier
                .verticalScroll(scrollState)
                .padding(AppDimensions.Padding.screen),
    ) {
        if (uiState.currentQuestionNumber != null && uiState.currentSession != null) {
            QuizProgressBar(
                currentQuestion = uiState.currentQuestionNumber,
                totalQuestions = uiState.currentSession.totalQuestions,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(AppDimensions.Spacing.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Card(
                shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
                colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                    ),
            ) {
                LabelMediumText(
                    text = "Score: ${uiState.score}",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(
                        horizontal = AppDimensions.Padding.medium,
                        vertical = AppDimensions.Padding.xs
                    ),
                )
            }

            Card(
                shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
                colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f),
                    ),
            ) {
                LabelMediumText(
                    text = formatTime(uiState.timeSpent),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(
                        horizontal = AppDimensions.Padding.medium,
                        vertical = AppDimensions.Padding.xs
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimensions.Spacing.large))

        AnimatedContent(
            targetState = uiState.currentQuestionNumber ?: 0,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            },
            label = "question_transition",
        ) { questionNumber ->
            if (currentQuestion != null && questionNumber > 0) {
                QuizQuestionCard(
                    question = currentQuestion,
                    selectedAnswer = uiState.currentAnswer,
                    onAnswerSelected = onAnswerSelected,
                    showResult = uiState.showExplanation,
                    answerResult = uiState.answerResult,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimensions.Spacing.large))

        if (uiState.showExplanation && uiState.answerResult != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            if (uiState.answerResult.correct) {
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                            } else {
                                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                            },
                    ),
            ) {
                Column(
                    modifier = Modifier.padding(AppDimensions.Padding.xl),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = if (uiState.answerResult.correct) Icons.Default.CheckCircle else Icons.Default.Close,
                            contentDescription = null,
                            tint =
                                if (uiState.answerResult.correct) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.error
                                },
                            modifier = Modifier.size(AppDimensions.IconSize.large),
                        )

                        Spacer(modifier = Modifier.width(AppDimensions.Spacing.small))

                        TitleMediumText(
                            text = uiState.answerResult.message,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }

                    Spacer(modifier = Modifier.height(AppDimensions.Spacing.medium))

                    BodyMediumText(
                        text = "Correct Answer: ${uiState.answerResult.correctAnswer} - ${uiState.answerResult.correctOptionText}",
                        color = MaterialTheme.colorScheme.primary,
                    )

                    Spacer(modifier = Modifier.height(AppDimensions.Spacing.small))

                    BodyMediumText(
                        text = uiState.answerResult.explanation,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(AppDimensions.Spacing.large))

        if (uiState.showExplanation) {
            PrimaryButton(
                text = if (uiState.answerResult?.hasNextQuestion == true) "Next Question" else "View Results",
                onClick = onNextQuestion,
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            PrimaryButton(
                text = "Submit Answer",
                onClick = onSubmitAnswer,
                enabled = uiState.currentAnswer.isNotEmpty() && !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(AppDimensions.Spacing.xl))
    }
}

private fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format(Locale.US, "%02d:%02d", minutes, remainingSeconds)
}