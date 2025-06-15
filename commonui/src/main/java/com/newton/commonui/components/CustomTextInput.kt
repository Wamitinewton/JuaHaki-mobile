package com.newton.commonui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.newton.core.enums.TextFieldSize

/**
 * @param value Current text value
 * @param onValueChange Callback when text changes
 * @param modifier Modifier to be applied to the text field
 * @param label Optional label text
 * @param placeholder Optional placeholder text
 * @param leadingIcon Optional leading icon
 * @param trailingIcon Optional trailing icon
 * @param isError Whether the text field is in error state
 * @param errorMessage Error message to display
 * @param helperText Helper text to display below the field
 * @param enabled Whether the text field is enabled
 * @param readOnly Whether the text field is read-only
 * @param singleLine Whether the text field is single line
 * @param maxLines Maximum number of lines
 * @param minLines Minimum number of lines
 * @param keyboardOptions Keyboard options
 * @param keyboardActions Keyboard actions
 * @param visualTransformation Visual transformation (e.g., password)
 * @param interactionSource Interaction source
 * @param size Size variant of the text field
 * @param cornerRadius Corner radius of the text field
 * @param backgroundColor Background color of the text field
 * @param borderColor Border color of the text field
 * @param focusedBorderColor Border color when focused
 * @param errorBorderColor Border color when in error state
 * @param textStyle Text style for the input text
 * @param labelStyle Text style for the label
 * @param placeholderStyle Text style for the placeholder
 * @param helperTextStyle Text style for helper text
 * @param errorTextStyle Text style for error message
 * @param borderWidth Border width
 * @param focusedBorderWidth Border width when focused
 */
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    helperText: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    size: TextFieldSize = TextFieldSize.Medium,
    cornerRadius: Dp = 12.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    labelStyle: TextStyle = MaterialTheme.typography.labelMedium,
    placeholderStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    helperTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
    errorTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
    borderWidth: Dp = 1.dp,
    focusedBorderWidth: Dp = 2.dp,
) {
    var isFocused by remember { mutableStateOf(false) }

    val (height, horizontalPadding, verticalPadding) =
        when (size) {
            TextFieldSize.Small -> Triple(40.dp, 12.dp, 8.dp)
            TextFieldSize.Medium -> Triple(48.dp, 16.dp, 12.dp)
            TextFieldSize.Large -> Triple(56.dp, 20.dp, 16.dp)
        }

    val currentBorderColor =
        when {
            isError -> errorBorderColor
            isFocused -> focusedBorderColor
            else -> borderColor
        }

    val currentBorderWidth = if (isFocused && !isError) focusedBorderWidth else borderWidth

    Column(modifier = modifier) {
        label?.let {
            Text(
                text = it,
                style =
                    labelStyle.copy(
                        color = if (isError) errorBorderColor else MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                    ),
                modifier = Modifier.padding(bottom = 6.dp),
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(height)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
            enabled = enabled,
            readOnly = readOnly,
            textStyle =
                textStyle.copy(
                    color = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(height)
                            .clip(RoundedCornerShape(cornerRadius))
                            .background(
                                if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.6f),
                            ).border(
                                width = currentBorderWidth,
                                color = currentBorderColor,
                                shape = RoundedCornerShape(cornerRadius),
                            ).padding(horizontal = horizontalPadding, vertical = verticalPadding),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    leadingIcon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = if (isError) errorBorderColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        if (value.isEmpty() && placeholder != null) {
                            Text(
                                text = placeholder,
                                style =
                                    placeholderStyle.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                    ),
                            )
                        }
                        innerTextField()
                    }

                    trailingIcon?.let { icon ->
                        Spacer(modifier = Modifier.width(12.dp))
                        if (onTrailingIconClick != null) {
                            IconButton(
                                onClick = onTrailingIconClick,
                                modifier = Modifier.size(24.dp),
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                )
                            }
                        } else {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = if (isError) errorBorderColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            )
                        }
                    }
                }
            },
        )

        val supportingText =
            when {
                isError && errorMessage != null -> errorMessage
                helperText != null -> helperText
                else -> null
            }

        supportingText?.let { text ->
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                style = if (isError) errorTextStyle else helperTextStyle,
                color = if (isError) errorBorderColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(start = 4.dp),
            )
        }
    }
}

/**
 * Email TextField with validation
 */
@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Email",
    placeholder: String = "Enter your email",
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    size: TextFieldSize = TextFieldSize.Medium,
    leadingIcon: ImageVector?,
) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        isError = isError,
        errorMessage = errorMessage,
        enabled = enabled,
        size = size,
        leadingIcon = leadingIcon,
    )
}

/**
 * Password TextField with visibility toggle
 */
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter your password",
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    size: TextFieldSize = TextFieldSize.Medium,
    showVisibilityToggle: Boolean = true,
    visibilityIcon: ImageVector? = null,
    visibilityOffIcon: ImageVector? = null,
    leadingIcon: ImageVector?,
) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        trailingIcon =
            if (showVisibilityToggle) {
                if (isPasswordVisible) visibilityOffIcon else visibilityIcon
            } else {
                null
            },
        onTrailingIconClick =
            if (showVisibilityToggle) {
                onTogglePasswordVisibility
            } else {
                null
            },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        isError = isError,
        errorMessage = errorMessage,
        enabled = enabled,
        size = size,
        leadingIcon = leadingIcon,
    )
}


/**
 * Phone TextField with proper formatting
 */
@Composable
fun PhoneTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Phone Number",
    placeholder: String = "Enter your phone number",
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    size: TextFieldSize = TextFieldSize.Medium,
    leadingIcon: ImageVector?,
) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true,
        isError = isError,
        errorMessage = errorMessage,
        enabled = enabled,
        size = size,
        leadingIcon = leadingIcon,
    )
}
