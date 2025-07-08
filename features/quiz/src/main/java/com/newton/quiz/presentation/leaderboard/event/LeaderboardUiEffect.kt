package com.newton.quiz.presentation.leaderboard.event

import com.newton.core.utils.SnackbarUiEffect

sealed class LeaderboardUiEffect : SnackbarUiEffect {
    data object NavigateBack : LeaderboardUiEffect()

    data object ShowDatePickerDialog : LeaderboardUiEffect()
}
