package com.newton.quiz.presentation.quizinfo.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.commonui.ui.HandleUiEffects
import com.newton.commonui.ui.SnackbarData
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEffect
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent
import com.newton.quiz.presentation.quizinfo.viewmodel.QuizInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizInfoContainer(
    onStartQuiz: (String) -> Unit,
    onViewLeaderboard: () -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (SnackbarData) -> Unit,
    onShowToast: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
    viewModel: QuizInfoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (uiState.quizInfo == null && !uiState.isLoading && uiState.error == null) {
            viewModel.onEvent(QuizInfoUiEvent.OnLoadTodaysQuiz)
        }
    }

    HandleUiEffects(
        uiEffects = viewModel.uiEffect,
        onShowSnackbar = onShowSnackbar,
        onShowToast = onShowToast,
        onCustomEffect = { effect ->
            when (effect) {
                is QuizInfoUiEffect.NavigateToQuizGame -> {
                    effect.sessionId?.let { sessionId ->
                        if (sessionId.isNotEmpty()) {
                            onStartQuiz(sessionId)
                        }
                    }
                }
                is QuizInfoUiEffect.NavigateBack -> onNavigateBack()
            }
        },
    )

    QuizInfoScreen(
        uiState = uiState,
        onQuizInfoEvent = viewModel::onEvent,
        onViewLeaderboard = onViewLeaderboard,
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}