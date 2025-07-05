package com.newton.quiz.presentation.quizresults.event

sealed class QuizResultsUiEvent {
    data class OnLoadQuizResults(
        val sessionId: String,
    ) : QuizResultsUiEvent()

    data object OnLoadLeaderboard : QuizResultsUiEvent()

    data object OnRetryResults : QuizResultsUiEvent()

    data object OnRetryLeaderboard : QuizResultsUiEvent()

    data object OnClearError : QuizResultsUiEvent()

    data object OnNavigateToHome : QuizResultsUiEvent()

    data object OnNavigateToNewQuiz : QuizResultsUiEvent()

}
