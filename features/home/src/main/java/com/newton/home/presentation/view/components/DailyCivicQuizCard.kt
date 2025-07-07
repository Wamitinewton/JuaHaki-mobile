package com.newton.home.presentation.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.commonui.components.CustomCard
import com.newton.commonui.ui.ShimmerBox
import com.newton.commonui.ui.ShimmerText
import com.newton.core.enums.CardVariant
import com.newton.domain.models.quiz.QuizInfo
import com.newton.quiz.presentation.quizinfo.state.QuizInfoUiState

@Composable
fun DailyCivicQuizCard(
    uiState: QuizInfoUiState,
    onCardClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    when {
        uiState.isLoading -> {
            QuizCardShimmer(modifier = modifier)
        }
        uiState.error != null -> {
            QuizCardError(
                onRetry = onRetry,
                modifier = modifier
            )
        }
        uiState.quizInfo != null -> {
            QuizCardContent(
                quizInfo = uiState.quizInfo,
                onCardClick = onCardClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun QuizCardContent(
    quizInfo: QuizInfo?,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (quizInfo != null) {
        CustomCard(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 160.dp)
                .padding(horizontal = 16.dp),
            variant = CardVariant.Surface,
            onClick = if (quizInfo.isActive && !quizInfo.isExpired) onCardClick else null,
            contentPadding = PaddingValues(20.dp),
            gradient = if (quizInfo.isActive && !quizInfo.isExpired) {
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f),
                    )
                )
            } else null
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Daily Civic Quiz",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Test your civic knowledge",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    StatusBadge(quizInfo = quizInfo)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuizInfoItem(
                        icon = Icons.Default.Quiz,
                        label = "${quizInfo.totalQuestions} Questions",
                        modifier = Modifier.weight(1f)
                    )

                    QuizInfoItem(
                        icon = Icons.Default.AccessTime,
                        label = if (quizInfo.hasUserAttempted) "Completed" else quizInfo.timeRemaining,
                        modifier = Modifier.weight(1f)
                    )

                    if (quizInfo.hasUserAttempted && quizInfo.userLastAttempt != null) {
                        QuizInfoItem(
                            icon = Icons.Default.CheckCircle,
                            label = "${quizInfo.userLastAttempt?.score ?: 0}% Score",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                ActionButton(
                    quizInfo = quizInfo,
                    onCardClick = onCardClick
                )
            }
        }
    }
}

@Composable
private fun StatusBadge(
    quizInfo: QuizInfo,
    modifier: Modifier = Modifier
) {
    val (text, containerColor, contentColor) = when {
        quizInfo.hasUserAttempted -> Triple(
            "Completed",
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer
        )
        quizInfo.isExpired -> Triple(
            "Expired",
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer
        )
        !quizInfo.isActive -> Triple(
            "Inactive",
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant
        )
        else -> Triple(
            "Available",
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = containerColor
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = contentColor,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun QuizInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun ActionButton(
    quizInfo: QuizInfo,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (buttonText, isEnabled) = when {
        quizInfo.hasUserAttempted -> "View Results" to true
        !quizInfo.isActive || quizInfo.isExpired -> "Unavailable" to false
        else -> "Start Quiz" to true
    }

    Button(
        onClick = onCardClick,
        modifier = modifier.fillMaxWidth(),
        enabled = isEnabled,
        colors = if (isEnabled) {
            ButtonDefaults.buttonColors()
        } else {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
private fun QuizCardShimmer(
    modifier: Modifier = Modifier,
) {
    CustomCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp)
            .padding(horizontal = 16.dp),
        variant = CardVariant.Surface,
        contentPadding = PaddingValues(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ShimmerText(width = 140.dp, height = 20.dp)
                    ShimmerText(width = 120.dp, height = 14.dp)
                }
                ShimmerBox(
                    modifier = Modifier.size(width = 80.dp, height = 28.dp),
                    cornerRadius = 12.dp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(3) {
                    ShimmerText(width = 80.dp, height = 14.dp)
                }
            }

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                cornerRadius = 12.dp
            )
        }
    }
}

@Composable
private fun QuizCardError(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CustomCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp)
            .padding(horizontal = 16.dp),
        variant = CardVariant.Outlined,
        onClick = onRetry,
        contentPadding = PaddingValues(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Daily Civic Quiz",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Failed to load quiz",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )

            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Try Again")
            }
        }
    }
}