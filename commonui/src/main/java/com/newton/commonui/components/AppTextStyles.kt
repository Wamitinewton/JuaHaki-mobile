package com.newton.commonui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

/**
 * Centralized text components with predefined styles for consistent typography
 */

/**
 * Large display text - used for main headers and hero sections
 */
@Composable
fun DisplayLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.displayLarge.copy(
            fontWeight = FontWeight.Bold
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Medium display text - used for section headers
 */
@Composable
fun DisplayMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.Bold
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Small display text - used for prominent content headers
 */
@Composable
fun DisplaySmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.displaySmall.copy(
            fontWeight = FontWeight.Bold
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Large headline - used for page titles
 */
@Composable
fun HeadlineLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Medium headline - used for card titles and section headers
 */
@Composable
fun HeadlineMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.SemiBold
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Small headline - used for subsection headers
 */
@Composable
fun HeadlineSmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.SemiBold
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Large title - used for important component titles
 */
@Composable
fun TitleLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Medium
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Medium title - used for standard component titles
 */
@Composable
fun TitleMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Medium
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Small title - used for small component titles
 */
@Composable
fun TitleSmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Medium
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Large body text - used for prominent content
 */
@Composable
fun BodyLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Medium body text - used for standard content
 */
@Composable
fun BodyMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Small body text - used for secondary content
 */
@Composable
fun BodySmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Large label - used for button text and prominent labels
 */
@Composable
fun LabelLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.Medium
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Medium label - used for standard labels and tags
 */
@Composable
fun LabelMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Medium
        ),
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Small label - used for captions and small labels
 */
@Composable
fun LabelSmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}

/**
 * Custom text component with full control over styling
 */
@Composable
fun CustomText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = style,
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
    )
}