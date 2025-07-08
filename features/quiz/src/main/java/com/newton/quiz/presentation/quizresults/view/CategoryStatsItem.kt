package com.newton.quiz.presentation.quizresults.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.domain.models.quiz.CategoryStats

@Composable
fun CategoryStatsItem(
    categoryStats: CategoryStats,
    modifier: Modifier = Modifier,
) {
    val progressColor =
        when {
            categoryStats.percentage >= 80 -> Color(0xFF10B981)
            categoryStats.percentage >= 60 -> Color(0xFFF59E0B)
            else -> Color(0xFFEF4444)
        }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = categoryStats.category,
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = progressColor.copy(alpha = 0.15f),
            ) {
                Text(
                    text = "${categoryStats.correctAnswers}/${categoryStats.totalQuestions} (${categoryStats.percentage.toInt()}%)",
                    style =
                        MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = progressColor,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                )
            }
        }

        LinearProgressIndicator(
            progress = { (categoryStats.percentage / 100).toFloat() },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
            color = progressColor,
            trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
        )
    }
}
