package com.newton.commonui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.newton.core.enums.ButtonVariant

/**
 * Customizable Filled Button component
 *
 * @param text The text to display on the button
 * @param onClick Callback when button is clicked
 * @param modifier Modifier to be applied to the button
 * @param enabled Whether the button is enabled
 * @param leadingIcon Optional leading icon
 * @param trailingIcon Optional trailing icon
 * @param iconSpacing Spacing between icon and text
 * @param shape Shape of the button
 * @param colors Button colors
 * @param elevation Button elevation
 * @param contentPadding Content padding
 * @param textStyle Text style for the button text
 * @param minHeight Minimum height of the button
 */
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    iconSpacing: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(24.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    minHeight: Dp = 48.dp,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(minHeight),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
    ) {
        ButtonContent(
            text = text,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            iconSpacing = iconSpacing,
            textStyle = textStyle,
        )
    }
}

/**
 * Customizable Filled Tonal Button component
 *
 * @param text The text to display on the button
 * @param onClick Callback when button is clicked
 * @param modifier Modifier to be applied to the button
 * @param enabled Whether the button is enabled
 * @param leadingIcon Optional leading icon
 * @param trailingIcon Optional trailing icon
 * @param iconSpacing Spacing between icon and text
 * @param shape Shape of the button
 * @param colors Button colors
 * @param elevation Button elevation
 * @param contentPadding Content padding
 * @param textStyle Text style for the button text
 * @param minHeight Minimum height of the button
 */
@Composable
fun CustomFilledTonalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    iconSpacing: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(24.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.filledTonalButtonElevation(),
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    minHeight: Dp = 48.dp,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier.height(minHeight),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
    ) {
        ButtonContent(
            text = text,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            iconSpacing = iconSpacing,
            textStyle = textStyle,
        )
    }
}

/**
 * Customizable Elevated Button component
 *
 * @param text The text to display on the button
 * @param onClick Callback when button is clicked
 * @param modifier Modifier to be applied to the button
 * @param enabled Whether the button is enabled
 * @param leadingIcon Optional leading icon
 * @param trailingIcon Optional trailing icon
 * @param iconSpacing Spacing between icon and text
 * @param shape Shape of the button
 * @param colors Button colors
 * @param elevation Button elevation
 * @param border Button border
 * @param contentPadding Content padding
 * @param textStyle Text style for the button text
 * @param minHeight Minimum height of the button
 */
@Composable
fun CustomElevatedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    iconSpacing: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(24.dp),
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    minHeight: Dp = 48.dp,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier.height(minHeight),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
    ) {
        ButtonContent(
            text = text,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            iconSpacing = iconSpacing,
            textStyle = textStyle,
        )
    }
}

/**
 * Customizable Text Button component
 *
 * @param text The text to display on the button
 * @param onClick Callback when button is clicked
 * @param modifier Modifier to be applied to the button
 * @param enabled Whether the button is enabled
 * @param leadingIcon Optional leading icon
 * @param trailingIcon Optional trailing icon
 * @param iconSpacing Spacing between icon and text
 * @param shape Shape of the button
 * @param colors Button colors
 * @param elevation Button elevation
 * @param border Button border
 * @param contentPadding Content padding
 * @param textStyle Text style for the button text
 * @param minHeight Minimum height of the button
 */
@Composable
fun CustomTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    iconSpacing: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(24.dp),
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    minHeight: Dp = 40.dp,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.height(minHeight),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
    ) {
        ButtonContent(
            text = text,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            iconSpacing = iconSpacing,
            textStyle = textStyle,
        )
    }
}

/**
 * Internal composable for button content layout
 * Handles the arrangement of text and icons
 */
@Composable
private fun RowScope.ButtonContent(
    text: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    iconSpacing: Dp = 8.dp,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
) {
    leadingIcon?.let { icon ->
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
        )
        Spacer(modifier = Modifier.width(iconSpacing))
    }

    Text(
        text = text,
        style = textStyle,
    )

    trailingIcon?.let { icon ->
        Spacer(modifier = Modifier.width(iconSpacing))
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
        )
    }
}

/**
 * Creates a primary button with default Material 3 styling
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
) {
    CustomButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        textStyle =
            MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.SemiBold,
            ),
    )
}

/**
 * Creates a secondary button with tonal styling
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
) {
    CustomFilledTonalButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        textStyle =
            MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Medium,
            ),
    )
}

/**
 * Creates a compact button with smaller dimensions
 */
@Composable
fun CompactButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    variant: ButtonVariant = ButtonVariant.Filled,
) {
    val buttonModifier = modifier
    val contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    val minHeight = 36.dp
    val textStyle = MaterialTheme.typography.labelMedium

    when (variant) {
        ButtonVariant.Filled ->
            CustomButton(
                text = text,
                onClick = onClick,
                modifier = buttonModifier,
                enabled = enabled,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                contentPadding = contentPadding,
                minHeight = minHeight,
                textStyle = textStyle,
            )

        ButtonVariant.FilledTonal ->
            CustomFilledTonalButton(
                text = text,
                onClick = onClick,
                modifier = buttonModifier,
                enabled = enabled,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                contentPadding = contentPadding,
                minHeight = minHeight,
                textStyle = textStyle,
            )

        ButtonVariant.Elevated ->
            CustomElevatedButton(
                text = text,
                onClick = onClick,
                modifier = buttonModifier,
                enabled = enabled,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                contentPadding = contentPadding,
                minHeight = minHeight,
                textStyle = textStyle,
            )

        ButtonVariant.Text ->
            CustomTextButton(
                text = text,
                onClick = onClick,
                modifier = buttonModifier,
                enabled = enabled,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                contentPadding = contentPadding,
                minHeight = minHeight,
                textStyle = textStyle,
            )
    }
}
