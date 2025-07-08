package com.newton.quiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.newton.commonui.components.BodySmallText
import com.newton.commonui.components.TitleMediumText
import com.newton.commonui.theme.AppDimensions
import com.newton.domain.models.quiz.AnswerResult
import com.newton.domain.models.quiz.QuizQuestion

@Composable
fun QuizQuestionCard(
    question: QuizQuestion,
    selectedAnswer: String,
    onAnswerSelected: (String) -> Unit,
    showResult: Boolean = false,
    answerResult: AnswerResult? = null,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.medium),
    ) {
        Column(
            modifier = Modifier.padding(AppDimensions.Padding.xl),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                QuizCategoryChip(
                    category = question.category,
                    modifier = Modifier.weight(1f, false),
                )

                Spacer(modifier = Modifier.width(AppDimensions.Spacing.small))

                QuizDifficultyChip(difficulty = question.difficulty)
            }

            Spacer(modifier = Modifier.height(AppDimensions.Spacing.medium))

            TitleMediumText(
                text = question.questionText,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(AppDimensions.Spacing.xl))

            question.options.forEach { option ->
                QuizOptionItem(
                    option = option,
                    isSelected = selectedAnswer == option.optionLetter,
                    showResult = showResult,
                    isCorrect = answerResult?.correctAnswer == option.optionLetter,
                    isUserAnswer = selectedAnswer == option.optionLetter,
                    onOptionSelected = { onAnswerSelected(option.optionLetter) },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(AppDimensions.Spacing.medium))
            }

            if (question.sourceReference.isNotEmpty()) {
                Spacer(modifier = Modifier.height(AppDimensions.Spacing.small))

                BodySmallText(
                    text = "Source: ${question.sourceReference}",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
