package com.newton.quiz.presentation.quizinfo.event

sealed class QuizInfoUiEvent {
    data object OnLoadTodaysQuiz : QuizInfoUiEvent()

    data object OnRetryLoading : QuizInfoUiEvent()

    data object OnClearError : QuizInfoUiEvent()

    data object OnStartQuiz : QuizInfoUiEvent()
}
