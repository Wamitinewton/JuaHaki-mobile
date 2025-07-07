package com.newton.home.presentation.view.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.commonui.components.CivicLoadingIndicator
import com.newton.domain.models.quiz.QuizInfo
import com.newton.quiz.presentation.quizinfo.state.QuizInfoUiState

@Composable
fun DailyCivicQuizCard(
    uiState: QuizInfoUiState,
    onCardClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val shimmerOffset = remember { Animatable(0f) }

    val primaryColor = MaterialTheme.colorScheme.primary
    val outlineColor = MaterialTheme.colorScheme.outline
    val surfaceColor = MaterialTheme.colorScheme.surface

    LaunchedEffect(Unit) {
        shimmerOffset.animateTo(
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation =
                        tween(
                            durationMillis = 4000,
                            easing = LinearEasing,
                        ),
                    repeatMode = RepeatMode.Restart,
                ),
        )
    }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp)
                .clickable {
                    if (!uiState.isLoading && uiState.error == null) {
                        onCardClick()
                    }
                },
        shape = RoundedCornerShape(20.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 8.dp,
            ),
    ) {
        Box {
            Canvas(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .matchParentSize()
                        .clip(RoundedCornerShape(20.dp)),
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val strokeWidth = 2.dp.toPx()
                val cornerRadius = 20.dp.toPx()

                val animatedOffset = shimmerOffset.value * (canvasWidth + canvasHeight) * 2
                val totalPerimeter = (canvasWidth + canvasHeight) * 2

                val lightPositions =
                    listOf(
                        animatedOffset % totalPerimeter,
                        (animatedOffset + totalPerimeter * 0.3f) % totalPerimeter,
                        (animatedOffset + totalPerimeter * 0.7f) % totalPerimeter,
                    )

                lightPositions.forEach { position ->
                    val (x, y) =
                        when {
                            position <= canvasWidth -> Pair(position, 0f)
                            position <= canvasWidth + canvasHeight -> Pair(canvasWidth, position - canvasWidth)
                            position <= canvasWidth * 2 + canvasHeight ->
                                Pair(
                                    canvasWidth - (position - canvasWidth - canvasHeight),
                                    canvasHeight,
                                )
                            else -> Pair(0f, canvasHeight - (position - canvasWidth * 2 - canvasHeight))
                        }

                    val lightGradient =
                        Brush.radialGradient(
                            colors =
                                listOf(
                                    primaryColor.copy(alpha = 0.3f),
                                    primaryColor.copy(alpha = 0.1f),
                                    Color.Transparent,
                                ),
                            center = Offset(x, y),
                            radius = 60.dp.toPx(),
                        )

                    drawCircle(
                        brush = lightGradient,
                        radius = 30.dp.toPx(),
                        center = Offset(x, y),
                    )
                }

                drawRoundRect(
                    color = outlineColor.copy(alpha = 0.1f),
                    style =
                        Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round,
                        ),
                    cornerRadius =
                        androidx.compose.ui.geometry
                            .CornerRadius(cornerRadius),
                )
            }

            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .padding(1.dp)
                        .clip(RoundedCornerShape(19.dp))
                        .background(
                            brush =
                                Brush.verticalGradient(
                                    colors =
                                        listOf(
                                            surfaceColor,
                                            surfaceColor.copy(alpha = 0.98f),
                                        ),
                                ),
                        ),
            )

            when {
                uiState.isLoading -> {
                    DailyCivicQuizLoadingContent()
                }
                uiState.error != null -> {
                    DailyCivicQuizErrorContent(
                        error = uiState.error,
                        onRetry = onRetry
                    )
                }
                uiState.quizInfo != null -> {
                    DailyCivicQuizContent(
                        quizInfo = uiState.quizInfo
                    )
                }

            }
        }
    }
}

@Composable
private fun DailyCivicQuizLoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        CivicLoadingIndicator(
            message = "Loading today's quiz...",
            size = 60.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
private fun DailyCivicQuizErrorContent(
    error: String? = null,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Quiz,
            contentDescription = "Error",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Daily Civic Quiz",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                        ),
                    ),
                )
                .clickable { onRetry() }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Retry",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun DailyCivicQuizContent(
    quizInfo: QuizInfo? = null
) {
   if (quizInfo != null) {
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(20.dp),
       ) {
           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically,
           ) {
               Row(
                   verticalAlignment = Alignment.CenterVertically,
               ) {
                   Box(
                       modifier = Modifier
                           .size(48.dp)
                           .clip(RoundedCornerShape(12.dp))
                           .background(
                               brush = Brush.linearGradient(
                                   colors = listOf(
                                       MaterialTheme.colorScheme.primary,
                                       MaterialTheme.colorScheme.secondary,
                                   ),
                               ),
                           ),
                       contentAlignment = Alignment.Center,
                   ) {
                       Icon(
                           imageVector = Icons.Default.Quiz,
                           contentDescription = "Quiz Icon",
                           modifier = Modifier.size(24.dp),
                           tint = MaterialTheme.colorScheme.onPrimary,
                       )
                   }

                   Spacer(modifier = Modifier.width(12.dp))

                   Column {
                       Text(
                           text = quizInfo.title,
                           style = MaterialTheme.typography.titleMedium.copy(
                               fontWeight = FontWeight.Bold,
                           ),
                           color = MaterialTheme.colorScheme.onSurface,
                       )
                       Text(
                           text = quizInfo.description,
                           style = MaterialTheme.typography.bodySmall,
                           color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                           maxLines = 2
                       )
                   }
               }

               Box(
                   modifier = Modifier
                       .clip(RoundedCornerShape(16.dp))
                       .background(
                           when {
                               quizInfo.hasUserAttempted -> MaterialTheme.colorScheme.primaryContainer
                               quizInfo.isExpired -> MaterialTheme.colorScheme.errorContainer
                               !quizInfo.isActive -> MaterialTheme.colorScheme.surfaceVariant
                               else -> MaterialTheme.colorScheme.secondaryContainer
                           }
                       )
                       .padding(horizontal = 12.dp, vertical = 6.dp),
               ) {
                   Text(
                       text = when {
                           quizInfo.hasUserAttempted -> "Completed"
                           quizInfo.isExpired -> "Expired"
                           !quizInfo.isActive -> "Inactive"
                           else -> "Available"
                       },
                       style = MaterialTheme.typography.labelSmall.copy(
                           fontWeight = FontWeight.Medium,
                       ),
                       color = when {
                           quizInfo.hasUserAttempted -> MaterialTheme.colorScheme.onPrimaryContainer
                           quizInfo.isExpired -> MaterialTheme.colorScheme.onErrorContainer
                           !quizInfo.isActive -> MaterialTheme.colorScheme.onSurfaceVariant
                           else -> MaterialTheme.colorScheme.onSecondaryContainer
                       },
                   )
               }
           }

           Spacer(modifier = Modifier.height(20.dp))

           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceEvenly,
           ) {
               StatItem(
                   icon = Icons.Default.Quiz,
                   value = "${quizInfo.totalQuestions}",
                   label = "Questions",
                   color = MaterialTheme.colorScheme.primary,
               )

               if (quizInfo.userLastAttempt != null) {
                   StatItem(
                       icon = Icons.Default.CheckCircle,
                       value = "${quizInfo.userLastAttempt?.score}%",
                       label = "Last Score",
                       color = MaterialTheme.colorScheme.secondary,
                   )
               }

               if (!quizInfo.isExpired && quizInfo.isActive) {
                   StatItem(
                       icon = Icons.Default.AccessTime,
                       value = quizInfo.timeRemaining,
                       label = "Time Left",
                       color = MaterialTheme.colorScheme.tertiary,
                   )
               }
           }

           Spacer(modifier = Modifier.height(16.dp))

           Box(
               modifier = Modifier
                   .fillMaxWidth()
                   .clip(RoundedCornerShape(12.dp))
                   .background(
                       brush = Brush.horizontalGradient(
                           colors = when {
                               quizInfo.hasUserAttempted -> listOf(
                                   MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                   MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                               )
                               quizInfo.isExpired || !quizInfo.isActive -> listOf(
                                   MaterialTheme.colorScheme.surfaceVariant,
                                   MaterialTheme.colorScheme.surfaceVariant,
                               )
                               else -> listOf(
                                   MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                   MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                               )
                           }
                       ),
                   )
                   .padding(vertical = 12.dp),
               contentAlignment = Alignment.Center,
           ) {
               Text(
                   text = when {
                       quizInfo.hasUserAttempted -> "View Results"
                       quizInfo.isExpired -> "Quiz Expired"
                       !quizInfo.isActive -> "Quiz Inactive"
                       else -> "Start Today's Quiz"
                   },
                   style = MaterialTheme.typography.titleSmall.copy(
                       fontWeight = FontWeight.SemiBold,
                   ),
                   color = when {
                       quizInfo.isExpired || !quizInfo.isActive -> MaterialTheme.colorScheme.onSurfaceVariant
                       else -> MaterialTheme.colorScheme.primary
                   },
                   textAlign = TextAlign.Center,
               )
           }
       }
   }
}

@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = color,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            ),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
    }
}