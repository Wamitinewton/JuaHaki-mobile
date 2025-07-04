package com.newton.quiz.presentation.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        shape = RoundedCornerShape(16.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
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

                Spacer(modifier = Modifier.width(8.dp))

                QuizDifficultyChip(difficulty = question.difficulty)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = question.questionText,
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        lineHeight = 24.sp,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(20.dp))

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

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (question.sourceReference.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Source: ${question.sourceReference}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
