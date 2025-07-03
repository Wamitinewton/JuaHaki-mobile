package com.newton.quiz.presentation.state

import com.newton.domain.models.quiz.QuizLeaderboard

data class QuizLeaderboardUiState(
    val isLoading: Boolean = false,
    val leaderboard: QuizLeaderboard? = null,
    val error: String? = null
)