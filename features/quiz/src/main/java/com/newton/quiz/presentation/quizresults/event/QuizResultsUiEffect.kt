package com.newton.quiz.presentation.quizresults.event

import com.newton.core.utils.SnackbarUiEffect

sealed class QuizResultsUiEffect : SnackbarUiEffect {
    data object NavigateToHome : QuizResultsUiEffect()

    data object NavigateToNewQuiz : QuizResultsUiEffect()

    data class ShareResults(
        val shareText: String,
    ) : QuizResultsUiEffect()

    data object ShowLeaderboard : QuizResultsUiEffect()
}
