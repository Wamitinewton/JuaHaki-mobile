package com.newton.quiz.presentation.quizinfo.state

import com.newton.core.enums.ErrorType
import com.newton.domain.models.quiz.QuizInfo

data class QuizInfoUiState(
    val isLoading: Boolean = false,
    val quizInfo: QuizInfo? = null,
    val error: String? = null,
    val errorType: ErrorType? = null,
)
