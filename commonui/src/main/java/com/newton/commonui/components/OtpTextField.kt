package com.newton.commonui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * OTP Text Field component for entering verification codes
 *
 * @param value Current OTP value
 * @param onValueChange Callback when OTP changes
 * @param modifier Modifier to be applied to the component
 * @param otpLength Number of OTP digits/characters
 * @param enabled Whether the field is enabled
 * @param isError Whether the field shows error state
 * @param onOtpComplete Callback when OTP is complete
 * @param cellSize Size of each OTP cell
 * @param cellSpacing Spacing between cells
 * @param cornerRadius Corner radius of cells
 * @param backgroundColor Background color of cells
 * @param focusedBackgroundColor Background color when focused
 * @param borderColor Border color of cells
 * @param focusedBorderColor Border color when focused
 * @param errorBorderColor Border color when in error state
 * @param textStyle Text style for the OTP characters
 * @param borderWidth Border width
 * @param focusedBorderWidth Border width when focused
 */
@Composable
fun OtpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    otpLength: Int = 6,
    enabled: Boolean = true,
    isError: Boolean = false,
    onOtpComplete: ((String) -> Unit)? = null,
    cellSize: Dp = 48.dp,
    cellSpacing: Dp = 8.dp,
    cornerRadius: Dp = 12.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    focusedBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    textStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    borderWidth: Dp = 1.dp,
    focusedBorderWidth: Dp = 2.dp,
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(value, TextRange(value.length)))
    }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(value) {
        if (textFieldValue.text != value) {
            textFieldValue = TextFieldValue(value, TextRange(value.length))
        }
    }

    LaunchedEffect(value) {
        if (value.length == otpLength) {
            onOtpComplete?.invoke(value)
            keyboardController?.hide()
        }
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val filteredText =
                    newValue.text
                        .filter { it.isLetterOrDigit() }
                        .take(otpLength)
                        .uppercase()

                if (filteredText.length <= otpLength) {
                    textFieldValue =
                        TextFieldValue(
                            text = filteredText,
                            selection = TextRange(filteredText.length),
                        )
                    onValueChange(filteredText)
                }
            },
            modifier =
                Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
            enabled = enabled,
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
            keyboardActions =
                KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    },
                ),
            singleLine = true,
            cursorBrush = SolidColor(Color.Transparent),
            textStyle = textStyle.copy(color = Color.Transparent),
            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(cellSpacing),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    repeat(otpLength) { index ->
                        val char = value.getOrNull(index)?.toString() ?: ""
                        val isCurrentIndex = value.length == index
                        val isCellFocused = isFocused && isCurrentIndex

                        OtpCell(
                            char = char,
                            isFocused = isCellFocused,
                            isError = isError,
                            size = cellSize,
                            cornerRadius = cornerRadius,
                            backgroundColor = if (isCellFocused) focusedBackgroundColor else backgroundColor,
                            borderColor =
                                when {
                                    isError -> errorBorderColor
                                    isCellFocused -> focusedBorderColor
                                    else -> borderColor
                                },
                            borderWidth = if (isCellFocused && !isError) focusedBorderWidth else borderWidth,
                            textStyle = textStyle,
                            enabled = enabled,
                        )
                    }
                }
            },
        )
    }
}

@Composable
private fun OtpCell(
    char: String,
    isFocused: Boolean,
    isError: Boolean,
    size: Dp,
    cornerRadius: Dp,
    backgroundColor: Color,
    borderColor: Color,
    borderWidth: Dp,
    textStyle: TextStyle,
    enabled: Boolean,
) {
    Box(
        modifier =
            Modifier
                .size(size)
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.6f),
                )
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(cornerRadius),
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = char,
            style =
                textStyle.copy(
                    color =
                        if (enabled) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        },
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                ),
        )

        if (isFocused && char.isEmpty()) {
            Box(
                modifier =
                    Modifier
                        .width(2.dp)
                        .height(textStyle.fontSize.value.dp * 0.8f)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(1.dp),
                        ),
            )
        }
    }
}
