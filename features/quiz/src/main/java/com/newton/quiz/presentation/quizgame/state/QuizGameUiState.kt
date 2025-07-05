package com.newton.quiz.presentation.quizgame.state

import com.newton.domain.models.quiz.AnswerResult
import com.newton.domain.models.quiz.QuizSession

data class QuizGameUiState(
    val isLoading: Boolean = false,
    val currentSession: QuizSession? = null,
    val currentQuestionNumber: Int? = null,
    val currentAnswer: String = "",
    val score: Int = 0,
    val timeSpent: Long = 0,
    val showExplanation: Boolean = false,
    val answerResult: AnswerResult? = null,
    val error: String? = null,
)
