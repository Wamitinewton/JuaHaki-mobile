package com.newton.home.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.BodyMediumText
import com.newton.commonui.components.BodySmallText
import com.newton.commonui.components.HeadlineSmallText
import com.newton.commonui.components.LabelMediumText
import com.newton.commonui.components.TitleMediumText
import com.newton.commonui.theme.AppDimensions

data class ChatbotCategory(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val gradientColors: List<Color>,
    val onClick: () -> Unit,
)

@Composable
fun ChatbotCategoriesGrid(
    modifier: Modifier = Modifier,
    onElectionGuideClick: () -> Unit = {},
    onFinanceBillAnalysisClick: () -> Unit = {},
) {
    val categories =
        listOf(
            ChatbotCategory(
                title = "Election Guide",
                description = "Get insights on candidates, voting procedures, and election information",
                icon = Icons.Filled.HowToVote,
                gradientColors =
                    listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                    ),
                onClick = onElectionGuideClick,
            ),
            ChatbotCategory(
                title = "Finance Bill Analysis",
                description = "Understand financial policies, budget allocations, and economic impacts",
                icon = Icons.Filled.Analytics,
                gradientColors =
                    listOf(
                        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                    ),
                onClick = onFinanceBillAnalysisClick,
            ),
        )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = AppDimensions.Padding.screen),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.xs),
        ) {
            HeadlineSmallText(
                text = "Specialized AI Assistants",
                color = MaterialTheme.colorScheme.onSurface,
            )

            BodyMediumText(
                text = "Choose a specialized assistant for targeted help",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppDimensions.Padding.screen)
                    .height(300.dp),
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
        ) {
            categories.forEach { category ->
                ChatbotCategoryCard(
                    category = category,
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                )
            }
        }
    }
}

@Composable
private fun ChatbotCategoryCard(
    category: ChatbotCategory,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .clip(RoundedCornerShape(AppDimensions.CornerRadius.xl))
                .clickable { category.onClick() },
        shape = RoundedCornerShape(AppDimensions.CornerRadius.xl),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = AppDimensions.Elevation.large,
                pressedElevation = AppDimensions.Elevation.xl,
            ),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        brush =
                            Brush.linearGradient(
                                colors =
                                    category.gradientColors +
                                        listOf(
                                            MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                        ),
                            ),
                    ).padding(AppDimensions.Padding.xl),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppDimensions.Spacing.medium),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(AppDimensions.CornerRadius.large))
                                .background(
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = category.icon,
                            contentDescription = category.title,
                            modifier = Modifier.size(AppDimensions.IconSize.xl),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }

                    TitleMediumText(
                        text = category.title,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    BodySmallText(
                        text = category.description,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Surface(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                    shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        LabelMediumText(
                            text = "View Documents",
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}
