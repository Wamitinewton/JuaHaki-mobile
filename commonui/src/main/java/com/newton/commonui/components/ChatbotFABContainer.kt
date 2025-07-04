package com.newton.commonui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun ChatbotFABContainer(
    modifier: Modifier = Modifier,
    chatbotIconRes: Int,
    onFabClick: () -> Unit = {},
    showPrompts: Boolean = true,
    autoHidePromptsDuration: Long = 4000L,
) {
    var showPromptChips by remember { mutableStateOf(showPrompts) }
    var userHasInteracted by remember { mutableStateOf(false) }

    LaunchedEffect(showPrompts) {
        if (showPrompts && !userHasInteracted) {
            showPromptChips = true
        }
    }

    Column(
        modifier =
            modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (showPromptChips) {
                                showPromptChips = false
                                userHasInteracted = true
                            }
                        },
                    )
                },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (showPrompts) {
            PromptChips(
                isVisible = showPromptChips && !userHasInteracted,
                onUserInteraction = {
                    showPromptChips = false
                    userHasInteracted = true
                },
                autoHideDurationMs = autoHidePromptsDuration,
            )
        }

        AnimatedChatbotFAB(
            chatbotIconRes = chatbotIconRes,
            onFabClick = {
                if (showPromptChips) {
                    showPromptChips = false
                    userHasInteracted = true
                }
                onFabClick()
            },
        )
    }
}
