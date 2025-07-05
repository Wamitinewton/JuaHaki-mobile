package com.newton.quiz.presentation.quizresults.state

import com.newton.core.enums.ErrorType
import com.newton.domain.models.quiz.QuizLeaderboard
import com.newton.domain.models.quiz.UserQuizSummary

data class QuizResultsUiState(
    val currentSessionId: String? = null,
    val isLoading: Boolean = false,
    val quizSummary: UserQuizSummary? = null,
    val error: String? = null,
    val errorType: ErrorType? = null,
    val isLoadingLeaderboard: Boolean = false,
    val leaderboard: QuizLeaderboard? = null,
    val leaderboardError: String? = null,
)