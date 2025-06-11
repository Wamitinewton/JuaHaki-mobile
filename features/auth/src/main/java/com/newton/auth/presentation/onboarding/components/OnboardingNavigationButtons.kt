package com.newton.auth.presentation.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.CustomButton
import com.newton.commonui.components.CustomFilledTonalButton

@Composable
fun OnboardingNavigationButtons(
    currentPage: Int,
    totalPages: Int,
    onSkip: () -> Unit,
    onNext: () -> Unit,
    onGetStarted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isLastPage = currentPage == totalPages - 1

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!isLastPage) {
            CustomButton(
                text = "Skip",
                onClick = onSkip,
                colors =
                    ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                textStyle = MaterialTheme.typography.labelLarge,
                minHeight = 48.dp,
            )
        } else {
            Spacer(modifier = Modifier.width(64.dp))
        }

        PageIndicator(
            pageCount = totalPages,
            currentPage = currentPage,
        )

        if (isLastPage) {
            CustomButton(
                text = "Get Started",
                onClick = onGetStarted,
                shape = RoundedCornerShape(24.dp),
                textStyle =
                    MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                minHeight = 48.dp,
            )
        } else {
            CustomFilledTonalButton(
                text = "Next",
                onClick = onNext,
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                shape = RoundedCornerShape(24.dp),
                textStyle = MaterialTheme.typography.labelLarge,
                minHeight = 48.dp,
                iconSpacing = 4.dp,
            )
        }
    }
}
