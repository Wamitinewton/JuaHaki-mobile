package com.newton.auth.presentation.login.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.core.enums.LoginMode

@Composable
fun LoginModeSelector(
    selectedMode: LoginMode,
    onModeChange: (LoginMode) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            LoginModeButton(
                text = "Email",
                isSelected = selectedMode == LoginMode.EMAIL,
                onClick = { onModeChange(LoginMode.EMAIL) },
                enabled = enabled,
                modifier = Modifier.weight(1f),
            )

            LoginModeButton(
                text = "Username",
                isSelected = selectedMode == LoginMode.USERNAME,
                onClick = { onModeChange(LoginMode.USERNAME) },
                enabled = enabled,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
fun LoginModeButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean,
) {
    val backgroundColor =
        if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surface
        }

    val textColor =
        if (isSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurface
        }

    val alpha = if (enabled) 1f else 0.6f

    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor.copy(alpha = alpha))
                .border(
                    width = if (isSelected) 0.dp else 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = alpha),
                    shape = RoundedCornerShape(8.dp),
                ).clickable(enabled = enabled) { onClick() }
                .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    fontWeight =
                        if (isSelected) {
                            FontWeight.SemiBold
                        } else {
                            FontWeight.Medium
                        },
                    color = textColor.copy(alpha = alpha),
                ),
        )
    }
}
