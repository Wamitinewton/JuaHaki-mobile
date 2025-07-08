package com.newton.quiz.presentation.quizhistory.state

import com.newton.core.enums.ErrorType
import com.newton.domain.models.quiz.UserQuizSummary

data class QuizHistoryUiState(
    val historyState: QuizHistoryMetadataState = QuizHistoryMetadataState(),
    val detailsState: QuizDetailsState = QuizDetailsState(),
)

data class QuizHistoryMetadataState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val quizHistory: List<UserQuizSummary>? = emptyList(),
    val error: String? = null,
    val errorType: ErrorType? = null,
)

data class QuizDetailsState(
    val isLoading: Boolean = false,
    val quizDetails: UserQuizSummary? = null,
    val currentSessionId: String? = null,
    val error: String? = null,
    val errorType: ErrorType? = null,
)
