package com.newton.quiz.presentation.leaderboard.event

import java.time.LocalDate

sealed class LeaderboardUiEvent {
    data object OnLoadTodaysLeaderboard : LeaderboardUiEvent()

    data class OnLoadLeaderboardByDate(
        val date: LocalDate,
    ) : LeaderboardUiEvent()

    data object OnRefreshLeaderboard : LeaderboardUiEvent()

    data object OnShowDatePicker : LeaderboardUiEvent()

    data object OnHideDatePicker : LeaderboardUiEvent()

    data class OnDateSelected(
        val date: LocalDate,
    ) : LeaderboardUiEvent()

    data object OnRetryLoading : LeaderboardUiEvent()

    data object OnClearError : LeaderboardUiEvent()

    data object OnNavigateBack : LeaderboardUiEvent()
}
