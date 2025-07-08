package com.newton.quiz.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.newton.commonui.components.LabelSmallText
import com.newton.commonui.theme.AppDimensions
import com.newton.core.enums.QuizDifficulty

@Composable
fun QuizDifficultyChip(
    difficulty: String,
    modifier: Modifier = Modifier,
) {
    val difficultyEnum =
        when (difficulty.lowercase()) {
            "easy" -> QuizDifficulty.EASY
            "medium" -> QuizDifficulty.MEDIUM
            "hard" -> QuizDifficulty.HARD
            else -> QuizDifficulty.MEDIUM
        }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        color = difficultyEnum.color.copy(alpha = 0.15f),
    ) {
        LabelSmallText(
            text = difficultyEnum.displayName,
            color = difficultyEnum.color,
            modifier =
                Modifier.padding(
                    horizontal = AppDimensions.Padding.medium,
                    vertical = AppDimensions.Padding.xs,
                ),
        )
    }
}
