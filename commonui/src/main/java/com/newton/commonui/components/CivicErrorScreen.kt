package com.newton.commonui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.core.enums.ErrorType

@Composable
fun CivicErrorScreen(
    errorMessage: String,
    errorType: ErrorType? = null,
    onRetry: (() -> Unit)? = null,
    retryText: String = "Try Again",
    modifier: Modifier = Modifier,
) {
    val errorInfo = getErrorInfo(errorType, errorMessage)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            shape = RoundedCornerShape(20.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(80.dp)
                            .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = errorInfo.icon,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.error,
                    )
                }

                Text(
                    text = errorInfo.title,
                    style =
                        MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = errorInfo.civicMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                )

                if (errorMessage.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f),
                    ) {
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }

                if (onRetry != null) {
                    Spacer(modifier = Modifier.height(8.dp))

                    PrimaryButton(
                        text = retryText,
                        onClick = onRetry,
                        leadingIcon = Icons.Default.Refresh,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

private data class ErrorInfo(
    val icon: ImageVector,
    val title: String,
    val civicMessage: String,
)

private fun getErrorInfo(
    errorType: ErrorType?,
    errorMessage: String,
): ErrorInfo =
    when (errorType) {
        ErrorType.CONNECTIVITY ->
            ErrorInfo(
                icon = Icons.Default.WifiOff,
                title = "Connection Required",
                civicMessage = "Your civic education journey requires an internet connection. Please check your network and try again.",
            )

        ErrorType.TIMEOUT ->
            ErrorInfo(
                icon = Icons.Default.Wifi,
                title = "Request Timeout",
                civicMessage = "The request is taking longer than expected. Just like democracy, good things take time. Please try again.",
            )

        ErrorType.AUTHENTICATION ->
            ErrorInfo(
                icon = Icons.Default.ErrorOutline,
                title = "Authentication Required",
                civicMessage = "Your session has expired. Please log in again to continue your civic education journey.",
            )

        ErrorType.AUTHORIZATION ->
            ErrorInfo(
                icon = Icons.Default.ErrorOutline,
                title = "Access Denied",
                civicMessage = "You don't have permission to access this content. Every citizen has rights, but this isn't one of them right now.",
            )

        ErrorType.NOT_FOUND ->
            ErrorInfo(
                icon = Icons.Default.ErrorOutline,
                title = "Content Not Found",
                civicMessage = "The civic content you're looking for seems to have gone missing. Let's get you back on track.",
            )

        ErrorType.SERVER_ERROR ->
            ErrorInfo(
                icon = Icons.Default.ErrorOutline,
                title = "Service Unavailable",
                civicMessage = "Our civic education servers are experiencing issues. Even the best institutions need maintenance sometimes.",
            )

        ErrorType.VALIDATION ->
            ErrorInfo(
                icon = Icons.Default.ErrorOutline,
                title = "Input Error",
                civicMessage = "Please check your input and try again. Accuracy is important in civic engagement.",
            )

        else ->
            ErrorInfo(
                icon = Icons.Default.ErrorOutline,
                title = "Something Went Wrong",
                civicMessage = "We encountered an unexpected issue. Your civic education journey continues - let's try again.",
            )
    }
