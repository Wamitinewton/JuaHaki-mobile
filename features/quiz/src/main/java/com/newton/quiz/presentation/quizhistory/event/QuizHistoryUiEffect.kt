package com.newton.quiz.presentation.quizhistory.event

import com.newton.core.utils.SnackbarUiEffect

sealed class QuizHistoryUiEffect : SnackbarUiEffect {
    data object NavigateBack : QuizHistoryUiEffect()

    data object NavigateBackFromDetails : QuizHistoryUiEffect()

    data class NavigateToQuizDetails(
        val sessionId: String,
    ) : QuizHistoryUiEffect()
}
