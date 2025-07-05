package com.newton.quiz.presentation.quizinfo.event

import com.newton.core.utils.SnackbarUiEffect

sealed class QuizInfoUiEffect : SnackbarUiEffect {
    data class NavigateToQuizGame(
        val sessionId: String? = null,
    ) : QuizInfoUiEffect()

    data object NavigateBack : QuizInfoUiEffect()
}
