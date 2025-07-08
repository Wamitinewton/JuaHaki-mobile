package com.newton.quiz.presentation.quizresults.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.extensions.sendErrorSnackbar
import com.newton.core.extensions.sendInfoSnackbar
import com.newton.domain.repository.quiz.QuizRepository
import com.newton.quiz.presentation.quizresults.event.QuizResultsUiEffect
import com.newton.quiz.presentation.quizresults.event.QuizResultsUiEvent
import com.newton.quiz.presentation.quizresults.state.QuizResultsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizResultsViewModel
    @Inject
    constructor(
        private val quizRepository: QuizRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(QuizResultsUiState())
        val uiState: StateFlow<QuizResultsUiState> = _uiState.asStateFlow()

        private val _uiEffect = Channel<QuizResultsUiEffect>()
        val uiEffect = _uiEffect.receiveAsFlow()

        /**
         * Handles all UI events
         */
        fun onEvent(event: QuizResultsUiEvent) {
            when (event) {
                is QuizResultsUiEvent.OnLoadQuizResults -> loadQuizResults(event.sessionId)
                QuizResultsUiEvent.OnLoadLeaderboard -> loadLeaderboard()
                QuizResultsUiEvent.OnRetryResults -> retryResults()
                QuizResultsUiEvent.OnRetryLeaderboard -> loadLeaderboard()
                QuizResultsUiEvent.OnClearError -> clearError()
                QuizResultsUiEvent.OnNavigateToHome -> navigateToHome()
                QuizResultsUiEvent.OnNavigateToNewQuiz -> navigateToNewQuiz()
            }
        }

        private fun loadQuizResults(sessionId: String) {
            if (sessionId.isBlank()) {
                viewModelScope.launch {
                    _uiEffect.sendErrorSnackbar("Invalid session ID")
                }
                return
            }

            _uiState.value = _uiState.value.copy(currentSessionId = sessionId)

            viewModelScope.launch {
                try {
                    _uiEffect.sendInfoSnackbar("Loading your quiz results...")

                    quizRepository.getQuizResults(sessionId).collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = isLoading,
                                        error = null,
                                    )
                            },
                            onSuccess = { quizSummary ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        quizSummary = quizSummary,
                                        error = null,
                                    )

                                onEvent(QuizResultsUiEvent.OnLoadLeaderboard)
                            },
                            onError = { message, _, _ ->
                                val errorMsg = message ?: "Failed to load quiz results"
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        error = errorMsg,
                                    )

                                viewModelScope.launch {
                                    _uiEffect.sendErrorSnackbar(
                                        message = errorMsg,
                                        actionLabel = "Retry",
                                        onActionClick = { retryResults() },
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
                            onActionClick = { retryResults() },
                        )
                    }
                }
            }
        }

        private fun loadLeaderboard() {
            viewModelScope.launch {
                try {
                    quizRepository.getTodaysLeaderboard().collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value = _uiState.value.copy(isLoadingLeaderboard = isLoading)
                            },
                            onSuccess = { leaderboard ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoadingLeaderboard = false,
                                        leaderboard = leaderboard,
                                        leaderboardError = null,
                                    )
                            },
                            onError = { message, _, _ ->
                                val errorMsg = message ?: "Failed to load leaderboard"
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoadingLeaderboard = false,
                                        leaderboardError = errorMsg,
                                    )
                            },
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoadingLeaderboard = false,
                            leaderboardError = "An unexpected error occurred: ${e.localizedMessage}",
                        )
                }
            }
        }

        private fun retryResults() {
            val sessionId = _uiState.value.currentSessionId
            if (!sessionId.isNullOrBlank()) {
                loadQuizResults(sessionId)
            }
        }

        private fun clearError() {
            _uiState.value =
                _uiState.value.copy(
                    error = null,
                    leaderboardError = null,
                )
        }

        private fun navigateToHome() {
            viewModelScope.launch {
                _uiEffect.send(QuizResultsUiEffect.NavigateToHome)
            }
        }

        private fun navigateToNewQuiz() {
            viewModelScope.launch {
                _uiEffect.send(QuizResultsUiEffect.NavigateToNewQuiz)
            }
        }

        fun resetState() {
            _uiState.value = QuizResultsUiState()
        }
    }
