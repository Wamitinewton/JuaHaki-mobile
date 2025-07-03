package com.newton.quiz.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.domain.models.quiz.QuizOption

@Composable
fun QuizOptionItem(
    option: QuizOption,
    isSelected: Boolean,
    showResult: Boolean = false,
    isCorrect: Boolean = false,
    isUserAnswer: Boolean = false,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        showResult && isCorrect -> MaterialTheme.colorScheme.primaryContainer
        showResult && isUserAnswer && !isCorrect -> MaterialTheme.colorScheme.errorContainer
        isSelected && !showResult -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    }

    val borderColor = when {
        showResult && isCorrect -> MaterialTheme.colorScheme.primary
        showResult && isUserAnswer && !isCorrect -> MaterialTheme.colorScheme.error
        isSelected && !showResult -> MaterialTheme.colorScheme.primary
        else -> Color.Transparent
    }

    val textColor = when {
        showResult && isCorrect -> MaterialTheme.colorScheme.onPrimaryContainer
        showResult && isUserAnswer && !isCorrect -> MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(
                width = if (borderColor != Color.Transparent) 2.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = !showResult) { onOptionSelected() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected || (showResult && (isCorrect || isUserAnswer))) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = option.optionLetter,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = if (isSelected || (showResult && (isCorrect || isUserAnswer))) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = option.optionText,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            modifier = Modifier.weight(1f)
        )

        if (showResult) {
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = if (isCorrect) Icons.Default.CheckCircle else if (isUserAnswer) Icons.Default.Close else Icons.Default.QuestionMark,
                contentDescription = null,
                tint = when {
                    isCorrect -> MaterialTheme.colorScheme.primary
                    isUserAnswer -> MaterialTheme.colorScheme.error
                    else -> Color.Transparent
                },
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
