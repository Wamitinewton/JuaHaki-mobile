package com.newton.quiz.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.newton.commonui.components.LabelSmallText
import com.newton.commonui.theme.AppDimensions

@Composable
fun QuizCategoryChip(
    category: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f),
    ) {
        LabelSmallText(
            text = category,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier =
                Modifier.padding(
                    horizontal = AppDimensions.Padding.medium,
                    vertical = AppDimensions.Padding.xs,
                ),
        )
    }
}
