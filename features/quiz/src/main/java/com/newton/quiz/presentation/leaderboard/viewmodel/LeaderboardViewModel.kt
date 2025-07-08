package com.newton.quiz.presentation.leaderboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.extensions.sendErrorSnackbar
import com.newton.core.extensions.sendInfoSnackbar
import com.newton.core.extensions.sendSuccessSnackbar
import com.newton.domain.repository.quiz.QuizRepository
import com.newton.quiz.presentation.leaderboard.event.LeaderboardUiEffect
import com.newton.quiz.presentation.leaderboard.event.LeaderboardUiEvent
import com.newton.quiz.presentation.leaderboard.state.LeaderboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel
    @Inject
    constructor(
        private val quizRepository: QuizRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(LeaderboardUiState())
        val uiState: StateFlow<LeaderboardUiState> = _uiState.asStateFlow()

        private val _uiEffect = Channel<LeaderboardUiEffect>()
        val uiEffect = _uiEffect.receiveAsFlow()

        init {
            onEvent(LeaderboardUiEvent.OnLoadTodaysLeaderboard)
        }

        /**
         * Handles all UI events
         */
        fun onEvent(event: LeaderboardUiEvent) {
            when (event) {
                LeaderboardUiEvent.OnLoadTodaysLeaderboard -> loadTodaysLeaderboard()
                is LeaderboardUiEvent.OnLoadLeaderboardByDate -> loadLeaderboardByDate(event.date)
                LeaderboardUiEvent.OnRefreshLeaderboard -> refreshLeaderboard()
                LeaderboardUiEvent.OnShowDatePicker -> showDatePicker()
                LeaderboardUiEvent.OnHideDatePicker -> hideDatePicker()
                is LeaderboardUiEvent.OnDateSelected -> onDateSelected(event.date)
                LeaderboardUiEvent.OnRetryLoading -> retryLoading()
                LeaderboardUiEvent.OnClearError -> clearError()
                LeaderboardUiEvent.OnNavigateBack -> navigateBack()
            }
        }

        private fun loadTodaysLeaderboard() {
            viewModelScope.launch {
                try {
                    quizRepository.getTodaysLeaderboard().collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = isLoading,
                                        error = null,
                                        selectedDate = null,
                                    )
                            },
                            onSuccess = { leaderboard ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        leaderboard = leaderboard,
                                        error = null,
                                        selectedDate = null,
                                    )
                            },
                            onError = { message, errorType, _ ->
                                val errorMsg = message ?: "Failed to load today's leaderboard"
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        error = errorMsg,
                                        errorType = errorType,
                                    )

                                viewModelScope.launch {
                                    _uiEffect.sendErrorSnackbar(
                                        message = errorMsg,
                                        actionLabel = "Retry",
                                        onActionClick = { onEvent(LeaderboardUiEvent.OnRetryLoading) },
                                    )
                                }
                            },
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            error = "An unexpected error occurred: ${e.localizedMessage}",
                        )

                    viewModelScope.launch {
                        _uiEffect.sendErrorSnackbar(
                            message = "An unexpected error occurred",
                            actionLabel = "Try Again",
                            onActionClick = { onEvent(LeaderboardUiEvent.OnRetryLoading) },
                        )
                    }
                }
            }
        }

        private fun loadLeaderboardByDate(date: LocalDate) {
            viewModelScope.launch {
                try {
                    _uiEffect.sendInfoSnackbar("Loading leaderboard for $date...")

                    quizRepository.getLeaderboard(date).collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoadingByDate = isLoading,
                                        error = null,
                                    )
                            },
                            onSuccess = { leaderboard ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoadingByDate = false,
                                        leaderboard = leaderboard,
                                        error = null,
                                        selectedDate = date,
                                        showDatePicker = false,
                                    )

                                viewModelScope.launch {
                                    _uiEffect.sendSuccessSnackbar("Leaderboard loaded successfully")
                                }
                            },
                            onError = { message, errorType, httpCode ->
                                val errorMsg = message ?: "Failed to load leaderboard for selected date"
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoadingByDate = false,
                                        error = errorMsg,
                                        errorType = errorType,
                                    )

                                viewModelScope.launch {
                                    _uiEffect.sendErrorSnackbar(
                                        message = errorMsg,
                                        actionLabel = "Retry",
                                        onActionClick = { loadLeaderboardByDate(date) },
                                    )
                                }
                            },
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoadingByDate = false,
                            error = "Failed to load leaderboard: ${e.localizedMessage}",
                        )

                    viewModelScope.launch {
                        _uiEffect.sendErrorSnackbar(
                            message = "Failed to load leaderboard for selected date",
                            actionLabel = "Try Again",
                            onActionClick = { loadLeaderboardByDate(date) },
                        )
                    }
                }
            }
        }

        private fun refreshLeaderboard() {
            _uiState.value = _uiState.value.copy(isRefreshing = true)

            val selectedDate = _uiState.value.selectedDate
            if (selectedDate != null) {
                loadLeaderboardByDate(selectedDate)
            } else {
                loadTodaysLeaderboard()
            }

            viewModelScope.launch {
                kotlinx.coroutines.delay(500)
                _uiState.value = _uiState.value.copy(isRefreshing = false)
            }
        }

        private fun showDatePicker() {
            _uiState.value = _uiState.value.copy(showDatePicker = true)
        }

        private fun hideDatePicker() {
            _uiState.value = _uiState.value.copy(showDatePicker = false)
        }

        private fun onDateSelected(date: LocalDate) {
            hideDatePicker()
            loadLeaderboardByDate(date)
        }

        private fun retryLoading() {
            val selectedDate = _uiState.value.selectedDate
            if (selectedDate != null) {
                loadLeaderboardByDate(selectedDate)
            } else {
                loadTodaysLeaderboard()
            }
        }

        private fun clearError() {
            _uiState.value = _uiState.value.copy(error = null, errorType = null)
        }

        private fun navigateBack() {
            viewModelScope.launch {
                _uiEffect.send(LeaderboardUiEffect.NavigateBack)
            }
        }
    }
