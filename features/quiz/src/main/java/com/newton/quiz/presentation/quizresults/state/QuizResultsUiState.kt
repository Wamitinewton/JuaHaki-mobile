package com.newton.quiz.presentation.quizresults.state

import com.newton.domain.models.quiz.UserQuizSummary

data class QuizResultsUiState(
    val isLoading: Boolean = false,
    val quizSummary: UserQuizSummary? = null,
    val error: String? = null,
)
