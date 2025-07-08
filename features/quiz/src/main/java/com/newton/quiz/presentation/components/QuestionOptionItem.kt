package com.newton.quiz.presentation.components

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.newton.commonui.components.BodyMediumText
import com.newton.commonui.components.LabelMediumText
import com.newton.commonui.theme.AppDimensions
import com.newton.domain.models.quiz.QuizOption

@Composable
fun QuizOptionItem(
    option: QuizOption,
    isSelected: Boolean,
    showResult: Boolean = false,
    isCorrect: Boolean = false,
    isUserAnswer: Boolean = false,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        when {
            showResult && isCorrect -> MaterialTheme.colorScheme.primaryContainer
            showResult && isUserAnswer && !isCorrect -> MaterialTheme.colorScheme.errorContainer
            isSelected && !showResult -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        }

    val borderColor =
        when {
            showResult && isCorrect -> MaterialTheme.colorScheme.primary
            showResult && isUserAnswer && !isCorrect -> MaterialTheme.colorScheme.error
            isSelected && !showResult -> MaterialTheme.colorScheme.primary
            else -> Color.Transparent
        }

    val textColor =
        when {
            showResult && isCorrect -> MaterialTheme.colorScheme.onPrimaryContainer
            showResult && isUserAnswer && !isCorrect -> MaterialTheme.colorScheme.onErrorContainer
            else -> MaterialTheme.colorScheme.onSurface
        }

    Row(
        modifier =
            modifier
                .clip(RoundedCornerShape(AppDimensions.CornerRadius.medium))
                .background(backgroundColor)
                .border(
                    width = if (borderColor != Color.Transparent) AppDimensions.BorderWidth.medium else AppDimensions.BorderWidth.thin,
                    color = borderColor,
                    shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
                ).clickable(enabled = !showResult) { onOptionSelected() }
                .padding(AppDimensions.Padding.large),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(AppDimensions.IconSize.xl)
                    .clip(CircleShape)
                    .background(
                        if (isSelected || (showResult && (isCorrect || isUserAnswer))) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        },
                    ),
            contentAlignment = Alignment.Center,
        ) {
            LabelMediumText(
                text = option.optionLetter,
                color =
                    if (isSelected || (showResult && (isCorrect || isUserAnswer))) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
            )
        }

        Spacer(modifier = Modifier.width(AppDimensions.Spacing.medium))

        BodyMediumText(
            text = option.optionText,
            color = textColor,
            modifier = Modifier.weight(1f),
        )

        if (showResult) {
            Spacer(modifier = Modifier.width(AppDimensions.Spacing.small))

            Icon(
                imageVector =
                    if (isCorrect) {
                        Icons.Default.CheckCircle
                    } else if (isUserAnswer) {
                        Icons.Default.Close
                    } else {
                        Icons.Default.QuestionMark
                    },
                contentDescription = null,
                tint =
                    when {
                        isCorrect -> MaterialTheme.colorScheme.primary
                        isUserAnswer -> MaterialTheme.colorScheme.error
                        else -> Color.Transparent
                    },
                modifier = Modifier.size(AppDimensions.IconSize.medium),
            )
        }
    }
}
