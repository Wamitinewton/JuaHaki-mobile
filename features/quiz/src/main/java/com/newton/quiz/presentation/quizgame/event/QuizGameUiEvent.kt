package com.newton.quiz.presentation.quizgame.event

sealed class QuizGameUiEvent {
    data class OnInitializeQuiz(
        val sessionId: String?,
    ) : QuizGameUiEvent()

    data class OnSelectAnswer(
        val answer: String,
    ) : QuizGameUiEvent()

    data object OnSubmitAnswer : QuizGameUiEvent()

    data object OnNextQuestion : QuizGameUiEvent()

    data object OnAbandonQuiz : QuizGameUiEvent()

    data object OnClearError : QuizGameUiEvent()

    data object OnNavigateToResults : QuizGameUiEvent()

    data object OnNavigateBack : QuizGameUiEvent()
}
