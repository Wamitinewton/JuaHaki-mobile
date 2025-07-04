package com.newton.quiz.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.core.enums.QuizPerformanceLevel
import com.newton.domain.models.quiz.LeaderboardEntry

@Composable
fun LeaderboardItem(
    entry: LeaderboardEntry,
    modifier: Modifier = Modifier,
) {
    val performanceColor =
        when (entry.performanceLevel.lowercase()) {
            "excellent" -> QuizPerformanceLevel.EXCELLENT.color
            "good" -> QuizPerformanceLevel.GOOD.color
            "fair" -> QuizPerformanceLevel.FAIR.color
            else -> QuizPerformanceLevel.NEEDS_IMPROVEMENT.color
        }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (entry.isCurrentUser) {
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = if (entry.isCurrentUser) 6.dp else 2.dp,
            ),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            brush =
                                Brush.linearGradient(
                                    colors =
                                        when (entry.rank) {
                                            1 -> listOf(Color(0xFFFFD700), Color(0xFFFFA500))
                                            2 -> listOf(Color(0xFFC0C0C0), Color(0xFF808080))
                                            3 -> listOf(Color(0xFFCD7F32), Color(0xFF8B4513))
                                            else ->
                                                listOf(
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                                )
                                        },
                                ),
                        ),
                contentAlignment = Alignment.Center,
            ) {
                if (entry.rank <= 3) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                } else {
                    Text(
                        text = entry.rank.toString(),
                        style =
                            MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (entry.isCurrentUser) "You" else entry.firstName,
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (entry.isCurrentUser) FontWeight.Bold else FontWeight.Medium,
                        ),
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = entry.performanceLevel,
                    style = MaterialTheme.typography.bodySmall,
                    color = performanceColor,
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = "${entry.score}%",
                    style =
                        MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = MaterialTheme.colorScheme.primary,
                )

                Text(
                    text = entry.durationFormatted,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )
            }
        }
    }
}
