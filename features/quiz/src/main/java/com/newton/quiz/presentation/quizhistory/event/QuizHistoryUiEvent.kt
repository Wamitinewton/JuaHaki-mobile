package com.newton.quiz.presentation.quizhistory.event

sealed class QuizHistoryUiEvent {
    data object OnLoadQuizHistory : QuizHistoryUiEvent()

    data object OnRefreshHistory : QuizHistoryUiEvent()

    data object OnRetryLoadingHistory : QuizHistoryUiEvent()

    data object OnClearHistoryError : QuizHistoryUiEvent()

    data class OnLoadQuizDetails(
        val sessionId: String,
    ) : QuizHistoryUiEvent()

    data class OnRetryLoadingDetails(
        val sessionId: String,
    ) : QuizHistoryUiEvent()

    data object OnClearDetailsError : QuizHistoryUiEvent()

    data class OnNavigateToQuizDetails(
        val sessionId: String,
    ) : QuizHistoryUiEvent()

    data object OnNavigateBack : QuizHistoryUiEvent()

    data object OnNavigateBackFromDetails : QuizHistoryUiEvent()
}
