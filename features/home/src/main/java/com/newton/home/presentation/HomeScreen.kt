package com.newton.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.ChatbotFABContainer
import com.newton.commonui.theme.backgroundGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    chatbotIconRes: Int = com.newton.commonui.R.drawable.chatbot,
    onNavigateToChat: () -> Unit = {}
) {
    var hidePrompts by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = backgroundGradient()
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        hidePrompts = true
                    }
                )
            }
    ) {

        ChatbotFABContainer(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            chatbotIconRes = chatbotIconRes,
            onFabClick = {
                println("🤖 Chatbot FAB clicked - Opening main chat")
                onNavigateToChat()
            },
            showPrompts = !hidePrompts,
            autoHidePromptsDuration = 4000L
        )
    }
}
