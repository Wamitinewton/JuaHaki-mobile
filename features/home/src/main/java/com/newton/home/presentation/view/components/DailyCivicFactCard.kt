package com.newton.home.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.newton.commonui.components.BodySmallText
import com.newton.commonui.components.DisplayMediumText
import com.newton.commonui.components.FeatureCard
import com.newton.commonui.components.LabelMediumText
import com.newton.commonui.components.TitleMediumText
import com.newton.commonui.theme.AppDimensions

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
                .padding(horizontal = AppDimensions.Padding.screen),
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
                        shape = RoundedCornerShape(AppDimensions.CornerRadius.xl),
                    )
                    .clip(RoundedCornerShape(AppDimensions.CornerRadius.xl)),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(AppDimensions.Padding.xxl),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = "Quote",
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                        modifier = Modifier.size(AppDimensions.IconSize.large),
                    )

                    Spacer(modifier = Modifier.width(AppDimensions.Spacing.small))

                    TitleMediumText(
                        text = "Daily Civic Fact",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }

                Spacer(modifier = Modifier.height(AppDimensions.Spacing.medium))

                Surface(
                    shape = RoundedCornerShape(AppDimensions.CornerRadius.medium),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                    modifier = Modifier.wrapContentSize(),
                ) {
                    LabelMediumText(
                        text = fact.category,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier =
                            Modifier.padding(
                                horizontal = AppDimensions.Padding.medium,
                                vertical = AppDimensions.Padding.xs,
                            ),
                    )
                }

                Spacer(modifier = Modifier.height(AppDimensions.Spacing.medium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    DisplayMediumText(
                        text = "\"",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        modifier = Modifier.offset(y = (-AppDimensions.Spacing.small)),
                    )

                    Column(
                        modifier =
                            Modifier
                                .weight(1f)
                                .padding(horizontal = AppDimensions.Padding.small),
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

                    DisplayMediumText(
                        text = "\"",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        modifier = Modifier.offset(y = AppDimensions.Spacing.small),
                    )
                }

                Spacer(modifier = Modifier.height(AppDimensions.Spacing.medium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    BodySmallText(
                        text = "— ${fact.source}",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}
