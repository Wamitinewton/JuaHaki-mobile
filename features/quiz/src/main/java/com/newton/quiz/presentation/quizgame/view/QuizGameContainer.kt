package com.newton.quiz.presentation.quizgame.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.commonui.ui.HandleUiEffects
import com.newton.commonui.ui.SnackbarData
import com.newton.quiz.presentation.quizgame.event.QuizGameUiEffect
import com.newton.quiz.presentation.quizgame.event.QuizGameUiEvent
import com.newton.quiz.presentation.quizgame.viewmodel.QuizGameViewModel

@Composable
fun QuizGameContainer(
    sessionId: String,
    onQuizComplete: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (SnackbarData) -> Unit,
    onShowToast: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
    viewModel: QuizGameViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAbandonDialog by remember { mutableStateOf(false) }

    LaunchedEffect(sessionId) {
        viewModel.onEvent(QuizGameUiEvent.OnInitializeQuiz(sessionId))
    }

    HandleUiEffects(
        uiEffects = viewModel.uiEffect,
        onShowSnackbar = onShowSnackbar,
        onShowToast = onShowToast,
        onCustomEffect = { effect ->
            when (effect) {
                is QuizGameUiEffect.NavigateToResults -> onQuizComplete(effect.sessionId)
                is QuizGameUiEffect.NavigateBack -> onNavigateBack()
                is QuizGameUiEffect.ShowAbandonConfirmationDialog -> showAbandonDialog = true
            }
        },
    )

    QuizGameScreen(
        uiState = uiState,
        onQuizGameEvent = viewModel::onEvent,
        onNavigateBack = { viewModel.onEvent(QuizGameUiEvent.OnAbandonQuiz) },
        modifier = modifier,
    )

    if (showAbandonDialog) {
        AlertDialog(
            onDismissRequest = { showAbandonDialog = false },
            title = { Text("Abandon Quiz?") },
            text = { Text("Are you sure you want to abandon this quiz? Your progress will be lost.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showAbandonDialog = false
                        viewModel.confirmAbandonQuiz()
                    },
                ) {
                    Text("Abandon")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showAbandonDialog = false },
                ) {
                    Text("Continue Quiz")
                }
            },
        )
    }
}
