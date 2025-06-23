package com.newton.home.presentation.userstats.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ChatbotCategory(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val gradientColors: List<Color>,
    val onClick: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
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
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "Specialized AI Assistants",
                style =
                    MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
            )

            Text(
                text = "Choose a specialized assistant for targeted help",
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    ),
            )
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(300.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatbotCategoryCard(
    category: ChatbotCategory,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { category.onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 12.dp,
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
                    ).padding(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = category.icon,
                            contentDescription = category.title,
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }

                    Text(
                        text = category.title,
                        style =
                            MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            ),
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
                    Text(
                        text = category.description,
                        style =
                            MaterialTheme.typography.bodySmall.copy(
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                            ),
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
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "View Documents",
                            style =
                                MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                ),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}
