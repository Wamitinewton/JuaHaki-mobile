package com.newton.commonui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.newton.core.enums.CardVariant


@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    variant: CardVariant = CardVariant.Default,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(16.dp),
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    gradient: Brush? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val cardModifier = if (onClick != null) {
        modifier
            .clip(shape)
            .clickable(enabled = enabled) { onClick() }
    } else {
        modifier
    }

    Card(
        modifier = cardModifier,
        shape = shape,
        colors = when (variant) {
            CardVariant.Default -> colors
            CardVariant.Primary -> CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            )
            CardVariant.Secondary -> CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
            )
            CardVariant.Surface -> CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
            CardVariant.Outlined -> CardDefaults.outlinedCardColors()
        },
        elevation = when (variant) {
            CardVariant.Outlined -> CardDefaults.cardElevation(defaultElevation = 0.dp)
            else -> elevation
        },
        border = when (variant) {
            CardVariant.Outlined -> BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
            else -> border
        },
    ) {
        Box {
            gradient?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = it, shape = shape)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding),
                content = content
            )
        }
    }
}

/**
 * Compact card variant for small content
 */
@Composable
fun CompactCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    variant: CardVariant = CardVariant.Default,
    content: @Composable ColumnScope.() -> Unit,
) {
    CustomCard(
        modifier = modifier,
        variant = variant,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        contentPadding = PaddingValues(12.dp),
        content = content
    )
}

/**
 * Feature card for highlighting important content
 */
@Composable
fun FeatureCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    gradient: Brush? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CustomCard(
        modifier = modifier,
        variant = CardVariant.Surface,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        contentPadding = PaddingValues(20.dp),
        gradient = gradient,
        content = content
    )
}

/**
 * Info card for displaying information with subtle styling
 */
@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    CustomCard(
        modifier = modifier,
        variant = CardVariant.Secondary,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        contentPadding = PaddingValues(14.dp),
        content = content
    )
}

/**
 * Status card for displaying status information with color coding
 */
@Composable
fun StatusCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    statusColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable ColumnScope.() -> Unit,
) {
    CustomCard(
        modifier = modifier,
        variant = CardVariant.Outlined,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, statusColor.copy(alpha = 0.3f)),
        contentPadding = PaddingValues(16.dp),
        content = content
    )
}