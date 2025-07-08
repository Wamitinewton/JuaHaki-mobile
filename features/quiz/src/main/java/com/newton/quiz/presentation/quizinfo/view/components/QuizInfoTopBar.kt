package com.newton.quiz.presentation.quizinfo.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.TitleMediumText
import com.newton.commonui.components.TitleSmallText
import com.newton.commonui.theme.AppDimensions

@ExperimentalMaterial3Api
@Composable
fun QuizInfoTopBar(
    title: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        shadowElevation = AppDimensions.Elevation.small,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppDimensions.Padding.large,
                        vertical = AppDimensions.Padding.medium,
                    )
                    .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(AppDimensions.CornerRadius.medium)),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                onClick = onNavigateBack,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate back",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(AppDimensions.IconSize.medium),
                    )
                }
            }

            Spacer(modifier = Modifier.width(AppDimensions.Spacing.large))

            Column(modifier = Modifier.weight(1f)) {
                TitleSmallText(
                    text = "Daily Civic Quiz",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )

                Spacer(modifier = Modifier.height(2.dp))

                TitleMediumText(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Surface(
                shape = RoundedCornerShape(AppDimensions.CornerRadius.large),
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.8f),
            ) {
                Text(
                    text = "🇰🇪",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier =
                        Modifier.padding(
                            horizontal = AppDimensions.Padding.medium,
                            vertical = AppDimensions.Padding.xs,
                        ),
                )
            }
        }
    }
}
