package com.newton.auth.presentation.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingNavigationButtons(
    currentPage: Int,
    totalPages: Int,
    onSkip: () -> Unit,
    onNext: () -> Unit,
    onGetStarted: () -> Unit,
    modifier: Modifier = Modifier
) {

    val isLastPage = currentPage == totalPages - 1

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isLastPage) {
            TextButton(
                onClick = onSkip,
                modifier = Modifier.height(48.dp)
            ) {
                Text(
                    text = "Skip",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Spacer(modifier = Modifier.width(64.dp))
        }

        PageIndicator(
            pageCount = totalPages,
            currentPage = currentPage
        )

        if (isLastPage) {
            Button(
                onClick = onGetStarted,
                modifier = Modifier.height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        } else {
            FilledIconButton(
                onClick = onNext,
                modifier = Modifier.height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Next",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
    
}