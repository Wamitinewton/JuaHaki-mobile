package com.newton.home.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.commonui.components.FeatureCard

data class CivicFact(
    val fact: String,
    val source: String,
    val category: String,
)

@Composable
fun DailyCivicFactCard(
    fact: CivicFact =
        CivicFact(
            fact = "The Kenyan Constitution guarantees every citizen the right to access information held by the state and any information held by another person that is required for the exercise or protection of any right or fundamental freedom.",
            source = "Article 35, Constitution of Kenya 2010",
            category = "Right to Information",
        ),
    modifier: Modifier = Modifier,
) {
    FeatureCard(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        brush =
                            Brush.linearGradient(
                                colors =
                                    listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary,
                                    ),
                            ),
                        shape = RoundedCornerShape(20.dp),
                    ).clip(RoundedCornerShape(20.dp)),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = "Quote",
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                        modifier = Modifier.size(24.dp),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Daily Civic Fact",
                        style =
                            MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                            ),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                    modifier = Modifier.wrapContentSize(),
                ) {
                    Text(
                        text = fact.category,
                        style =
                            MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimary,
                            ),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "\"",
                        style =
                            MaterialTheme.typography.displayMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                                fontWeight = FontWeight.Bold,
                            ),
                        modifier = Modifier.offset(y = (-8).dp),
                    )

                    Column(
                        modifier =
                            Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp),
                    ) {
                        Text(
                            text = fact.fact,
                            style =
                                MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight.Medium,
                                ),
                            textAlign = TextAlign.Start,
                        )
                    }

                    Text(
                        text = "\"",
                        style =
                            MaterialTheme.typography.displayMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                                fontWeight = FontWeight.Bold,
                            ),
                        modifier = Modifier.offset(y = 8.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(
                        text = "— ${fact.source}",
                        style =
                            MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Light,
                            ),
                    )
                }
            }
        }
    }
}
