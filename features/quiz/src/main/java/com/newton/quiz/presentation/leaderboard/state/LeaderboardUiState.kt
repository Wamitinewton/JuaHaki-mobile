package com.newton.quiz.presentation.leaderboard.state

import com.newton.core.enums.ErrorType
import com.newton.domain.models.quiz.QuizLeaderboard
import java.time.LocalDate

data class LeaderboardUiState(
    val isLoading: Boolean = false,
    val leaderboard: QuizLeaderboard? = null,
    val error: String? = null,
    val errorType: ErrorType? = null,
    val selectedDate: LocalDate? = null,
    val isLoadingByDate: Boolean = false,
    val showDatePicker: Boolean = false,
    val isRefreshing: Boolean = false,
)
