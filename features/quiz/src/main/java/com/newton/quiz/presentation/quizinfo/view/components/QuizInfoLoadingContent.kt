package com.newton.quiz.presentation.quizinfo.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.BodyLargeText
import com.newton.commonui.components.CivicErrorScreen
import com.newton.commonui.components.PrimaryButton
import com.newton.commonui.components.TitleLargeText
import com.newton.commonui.theme.AppDimensions
import com.newton.commonui.ui.ShimmerBox
import com.newton.commonui.ui.ShimmerText
import com.newton.core.enums.ErrorType

@Composable
fun QuizInfoLoadingContent(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(AppDimensions.Padding.screen),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
    ) {
        Spacer(modifier = Modifier.height(AppDimensions.Spacing.medium))

        // Hero Section Shimmer
        QuizHeroSectionSkeleton(
            modifier = Modifier.fillMaxWidth(),
        )

        // Status Section Shimmer
        QuizStatusSectionSkeleton(
            modifier = Modifier.fillMaxWidth(),
        )

        // Actions Section Shimmer
        QuizActionsSectionSkeleton(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun QuizInfoErrorContent(
    error: String,
    errorType: ErrorType?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CivicErrorScreen(
        errorMessage = error,
        errorType = errorType,
        onRetry = onRetry,
        retryText = "Reload Quiz",
        modifier = modifier,
    )
}

@Composable
fun QuizInfoEmptyState(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            shape = RoundedCornerShape(AppDimensions.CornerRadius.xl),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.large),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.Padding.xl),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(AppDimensions.Padding.xxl),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
            ) {
                Surface(
                    shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier =
                            Modifier
                                .padding(AppDimensions.Padding.xl)
                                .size(48.dp),
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
                ) {
                    TitleLargeText(
                        text = "No Quiz Available",
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )

                    BodyLargeText(
                        text = "We couldn't find any quiz information at the moment. Please check your connection and try again.",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                    )
                }

                PrimaryButton(
                    text = "Try Again",
                    onClick = onRetry,
                    leadingIcon = Icons.Default.Refresh,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun QuizHeroSectionSkeleton(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.xl),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.large),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.Padding.xl),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ShimmerBox(
                    modifier =
                        Modifier
                            .width(120.dp)
                            .height(32.dp),
                    cornerRadius = AppDimensions.CornerRadius.large,
                )

                ShimmerBox(
                    modifier =
                        Modifier
                            .width(80.dp)
                            .height(32.dp),
                    cornerRadius = AppDimensions.CornerRadius.large,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
            ) {
                ShimmerText(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    height = 28.dp,
                )
                ShimmerText(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    height = 28.dp,
                )

                Spacer(modifier = Modifier.height(AppDimensions.Spacing.small))

                repeat(3) {
                    ShimmerText(
                        modifier = Modifier.fillMaxWidth(if (it == 2) 0.6f else 1f),
                        height = 20.dp,
                    )
                }
            }

            // Quick stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
            ) {
                repeat(2) {
                    ShimmerBox(
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(60.dp),
                        cornerRadius = AppDimensions.CornerRadius.medium,
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizStatusSectionSkeleton(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.medium),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.Padding.xl),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
            ) {
                ShimmerBox(
                    modifier = Modifier.size(56.dp),
                    cornerRadius = AppDimensions.CornerRadius.medium,
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.xs),
                ) {
                    ShimmerText(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        height = 24.dp,
                    )
                    ShimmerText(
                        modifier = Modifier.fillMaxWidth(0.4f),
                        height = 16.dp,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
            ) {
                repeat(2) {
                    ShimmerBox(
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(80.dp),
                        cornerRadius = AppDimensions.CornerRadius.medium,
                    )
                }
            }

            ShimmerBox(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                cornerRadius = AppDimensions.CornerRadius.large,
            )
        }
    }
}

@Composable
private fun QuizActionsSectionSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
    ) {
        // Primary action card
        Card(
            shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = AppDimensions.Elevation.medium),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(AppDimensions.Padding.xl),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.large),
            ) {
                ShimmerBox(
                    modifier = Modifier.size(80.dp),
                    cornerRadius = AppDimensions.CornerRadius.large,
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.small),
                ) {
                    ShimmerText(
                        modifier = Modifier.width(200.dp),
                        height = 24.dp,
                    )
                    ShimmerText(
                        modifier = Modifier.width(160.dp),
                        height = 16.dp,
                    )
                }

                ShimmerBox(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                    cornerRadius = AppDimensions.CornerRadius.large,
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
        ) {
            repeat(2) {
                ShimmerBox(
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(48.dp),
                    cornerRadius = AppDimensions.CornerRadius.large,
                )
            }
        }
    }
}
