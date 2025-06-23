package com.newton.commonui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun PromptChips(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    onUserInteraction: () -> Unit = {},
    autoHideDurationMs: Long = 4000L
) {
    val prompts = listOf(
        "What is Article 43?",
        "How is budget passed in Kenya?",
        "Rights during arrest"
    )

    var showChips by remember { mutableStateOf(isVisible) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            showChips = true
            delay(autoHideDurationMs)
            showChips = false
        }
    }

    LaunchedEffect(isVisible) {
        if (!isVisible) {
            showChips = false
        }
    }

    AnimatedVisibility(
        visible = showChips && isVisible,
        enter = fadeIn(
            animationSpec = tween(600, delayMillis = 500)
        ) + slideInVertically(
            animationSpec = tween(600, delayMillis = 500),
            initialOffsetY = { it / 2 }
        ),
        exit = fadeOut(
            animationSpec = tween(400)
        ) + slideOutVertically(
            animationSpec = tween(400),
            targetOffsetY = { it / 2 }
        )
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            prompts.forEachIndexed { index, prompt ->
                AnimatedPromptChip(
                    text = prompt,
                    delay = index * 150,
                    onClick = {
                        onUserInteraction()
                        showChips = false
                    }
                )
            }
        }
    }
}

@Composable
private fun AnimatedPromptChip(
    text: String,
    delay: Int,
    onClick: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delay.toLong())
        isVisible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "chip_alpha"
    )

    AssistChip(
        onClick = onClick,
        label = {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        },
        modifier = Modifier
            .alpha(alpha)
            .padding(horizontal = 4.dp),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(16.dp)
    )
}