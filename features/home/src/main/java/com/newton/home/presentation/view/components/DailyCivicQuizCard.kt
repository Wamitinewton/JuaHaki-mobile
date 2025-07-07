package com.newton.home.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
                error = uiState.error,
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
           modifier = modifier.fillMaxWidth()
               .padding(horizontal = 16.dp),
           variant = CardVariant.Surface,
           onClick = if (quizInfo.isActive && !quizInfo.isExpired) onCardClick else null,
           contentPadding = PaddingValues(16.dp),
           gradient = if (quizInfo.isActive && !quizInfo.isExpired) {
               Brush.horizontalGradient(
                   colors = listOf(
                       MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                       MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f),
                   )
               )
           } else null
       ) {
           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
           ) {
               Row(
                   modifier = Modifier.weight(1f),
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.spacedBy(12.dp)
               ) {
                   Box(
                       modifier = Modifier
                           .size(40.dp)
                           .clip(RoundedCornerShape(10.dp))
                           .background(
                               when {
                                   quizInfo.hasUserAttempted -> MaterialTheme.colorScheme.primaryContainer
                                   quizInfo.isExpired -> MaterialTheme.colorScheme.errorContainer
                                   !quizInfo.isActive -> MaterialTheme.colorScheme.surfaceVariant
                                   else -> MaterialTheme.colorScheme.primary
                               }
                           ),
                       contentAlignment = Alignment.Center
                   ) {
                       Icon(
                           imageVector = if (quizInfo.hasUserAttempted) Icons.Default.CheckCircle else Icons.Default.Quiz,
                           contentDescription = null,
                           modifier = Modifier.size(20.dp),
                           tint = when {
                               quizInfo.hasUserAttempted -> MaterialTheme.colorScheme.onPrimaryContainer
                               quizInfo.isExpired -> MaterialTheme.colorScheme.onErrorContainer
                               !quizInfo.isActive -> MaterialTheme.colorScheme.onSurfaceVariant
                               else -> MaterialTheme.colorScheme.onPrimary
                           }
                       )
                   }

                   Column(
                       verticalArrangement = Arrangement.spacedBy(2.dp)
                   ) {
                       Text(
                           text = "Daily Civic Quiz",
                           style = MaterialTheme.typography.titleSmall.copy(
                               fontWeight = FontWeight.SemiBold
                           ),
                           color = MaterialTheme.colorScheme.onSurface,
                           maxLines = 1,
                           overflow = TextOverflow.Ellipsis
                       )

                       Row(
                           verticalAlignment = Alignment.CenterVertically,
                           horizontalArrangement = Arrangement.spacedBy(4.dp)
                       ) {
                           if (!quizInfo.isExpired && quizInfo.isActive) {
                               Icon(
                                   imageVector = Icons.Default.AccessTime,
                                   contentDescription = null,
                                   modifier = Modifier.size(12.dp),
                                   tint = MaterialTheme.colorScheme.primary
                               )
                               Text(
                                   text = quizInfo.timeRemaining,
                                   style = MaterialTheme.typography.bodySmall.copy(
                                       fontSize = 11.sp,
                                       fontWeight = FontWeight.Medium
                                   ),
                                   color = MaterialTheme.colorScheme.primary
                               )
                           } else {
                               Text(
                                   text = when {
                                       quizInfo.hasUserAttempted -> "Completed today"
                                       quizInfo.isExpired -> "Expired"
                                       else -> "Inactive"
                                   },
                                   style = MaterialTheme.typography.bodySmall.copy(
                                       fontSize = 11.sp
                                   ),
                                   color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                               )
                           }
                       }
                   }
               }

               Surface(
                   shape = RoundedCornerShape(12.dp),
                   color = when {
                       quizInfo.hasUserAttempted -> MaterialTheme.colorScheme.primaryContainer
                       quizInfo.isExpired -> MaterialTheme.colorScheme.errorContainer
                       !quizInfo.isActive -> MaterialTheme.colorScheme.surfaceVariant
                       else -> MaterialTheme.colorScheme.secondaryContainer
                   }
               ) {
                   Text(
                       text = when {
                           quizInfo.hasUserAttempted -> "${quizInfo.userLastAttempt?.score ?: 0}%"
                           quizInfo.isExpired -> "Expired"
                           !quizInfo.isActive -> "Inactive"
                           else -> "${quizInfo.totalQuestions}Q"
                       },
                       style = MaterialTheme.typography.labelSmall.copy(
                           fontWeight = FontWeight.SemiBold,
                           fontSize = 10.sp
                       ),
                       color = when {
                           quizInfo.hasUserAttempted -> MaterialTheme.colorScheme.onPrimaryContainer
                           quizInfo.isExpired -> MaterialTheme.colorScheme.onErrorContainer
                           !quizInfo.isActive -> MaterialTheme.colorScheme.onSurfaceVariant
                           else -> MaterialTheme.colorScheme.onSecondaryContainer
                       },
                       modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                   )
               }
           }
       }
   }
}

@Composable
private fun QuizCardShimmer(
    modifier: Modifier = Modifier,
) {
    CustomCard(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        variant = CardVariant.Surface,
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ShimmerBox(
                    modifier = Modifier.size(40.dp),
                    cornerRadius = 10.dp,
                    colors = listOf(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                    )
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ShimmerText(
                        width = 120.dp,
                        height = 16.dp,
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                        )
                    )
                    ShimmerText(
                        width = 80.dp,
                        height = 12.dp,
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        )
                    )
                }
            }

            ShimmerBox(
                modifier = Modifier
                    .width(48.dp)
                    .height(24.dp),
                cornerRadius = 12.dp,
                colors = listOf(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                )
            )
        }
    }
}

@Composable
private fun QuizCardError(
    error: String? = null,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CustomCard(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        variant = CardVariant.Outlined,
        onClick = onRetry,
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.errorContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onErrorContainer
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Daily Civic Quiz",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Tap to retry",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp
                        ),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.errorContainer
            ) {
                Text(
                    text = error ?: "An unknow error occurred",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    ),
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}