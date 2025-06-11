package com.newton.auth.presentation.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle

@Composable
fun PrivacyTermsText(
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val annotatedString =
        buildAnnotatedString {
            append("By creating an account, you agree to our ")

            withLink(
                LinkAnnotation.Clickable(
                    tag = "terms",
                ) {
                    onTermsOfServiceClick()
                },
            ) {
                withStyle(
                    style =
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                        ),
                ) {
                    append("Terms of Service")
                }
            }

            append(" and ")

            withLink(
                LinkAnnotation.Clickable(
                    tag = "privacy",
                ) {
                    onPrivacyPolicyClick()
                },
            ) {
                withStyle(
                    style =
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                        ),
                ) {
                    append("Privacy Policy")
                }
            }

            append(".")
        }

    BasicText(
        text = annotatedString,
        style =
            MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
            ),
        modifier = modifier,
    )
}
