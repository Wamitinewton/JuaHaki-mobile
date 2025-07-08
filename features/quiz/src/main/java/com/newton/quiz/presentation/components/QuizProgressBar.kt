package com.newton.quiz.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.newton.commonui.components.BodyMediumText
import com.newton.commonui.theme.AppDimensions

@Composable
fun QuizProgressBar(
    currentQuestion: Int,
    totalQuestions: Int,
    modifier: Modifier = Modifier,
) {
    val progress by animateFloatAsState(
        targetValue = currentQuestion.toFloat() / totalQuestions.toFloat(),
        animationSpec = tween(durationMillis = 300, easing = LinearEasing),
        label = "progress",
    )

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BodyMediumText(
                text = "Question $currentQuestion of $totalQuestions",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )

            BodyMediumText(
                text = "${(progress * 100).toInt()}%",
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Spacer(modifier = Modifier.height(AppDimensions.Spacing.small))

        LinearProgressIndicator(
            progress = { progress },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.Spacing.small)
                    .clip(RoundedCornerShape(AppDimensions.CornerRadius.xs)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        )
    }
}