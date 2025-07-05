package com.newton.quiz.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
        shape = RoundedCornerShape(16.dp),
        color = difficultyEnum.color.copy(alpha = 0.15f),
    ) {
        Text(
            text = difficultyEnum.displayName,
            style =
                MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                ),
            color = difficultyEnum.color,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        )
    }
}
