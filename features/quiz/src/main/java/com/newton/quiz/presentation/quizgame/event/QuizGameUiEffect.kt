package com.newton.quiz.presentation.quizgame.event

import com.newton.core.utils.SnackbarUiEffect

sealed class QuizGameUiEffect : SnackbarUiEffect {
    data class NavigateToResults(
        val sessionId: String,
    ) : QuizGameUiEffect()

    data object NavigateBack : QuizGameUiEffect()

    data object ShowAbandonConfirmationDialog : QuizGameUiEffect()
}
