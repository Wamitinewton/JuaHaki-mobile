package com.newton.quiz.presentation.quizresults.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.commonui.ui.HandleUiEffects
import com.newton.commonui.ui.SnackbarData
import com.newton.quiz.presentation.quizresults.event.QuizResultsUiEffect
import com.newton.quiz.presentation.quizresults.event.QuizResultsUiEvent
import com.newton.quiz.presentation.quizresults.viewmodel.QuizResultsViewModel

@Composable
fun QuizResultsContainer(
    sessionId: String,
    onViewLeaderboard: () -> Unit,
    onRetakeQuiz: () -> Unit,
    onNavigateBack: () -> Unit,
    onShowSnackbar: (SnackbarData) -> Unit,
    onShowToast: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
    viewModel: QuizResultsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(sessionId) {
        viewModel.onEvent(QuizResultsUiEvent.OnLoadQuizResults(sessionId))
    }

    HandleUiEffects(
        uiEffects = viewModel.uiEffect,
        onShowSnackbar = onShowSnackbar,
        onShowToast = onShowToast,
        onCustomEffect = { effect ->
            when (effect) {
                is QuizResultsUiEffect.NavigateToHome -> onNavigateBack()
                is QuizResultsUiEffect.NavigateToNewQuiz -> onRetakeQuiz()
                is QuizResultsUiEffect.ShowLeaderboard -> onViewLeaderboard()
            }
        },
    )

    QuizResultsScreen(
        uiState = uiState,
        onQuizResultsEvent = viewModel::onEvent,
        onViewLeaderboard = onViewLeaderboard,
        onRetakeQuiz = onRetakeQuiz,
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    )
}
