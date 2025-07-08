package com.newton.quiz.presentation.quizhistory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.extensions.sendErrorSnackbar
import com.newton.core.extensions.sendInfoSnackbar
import com.newton.core.extensions.sendSuccessSnackbar
import com.newton.domain.repository.quiz.QuizRepository
import com.newton.quiz.presentation.quizhistory.event.QuizHistoryUiEffect
import com.newton.quiz.presentation.quizhistory.event.QuizHistoryUiEvent
import com.newton.quiz.presentation.quizhistory.state.QuizHistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizHistoryViewModel
    @Inject
    constructor(
        private val quizRepository: QuizRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(QuizHistoryUiState())
        val uiState: StateFlow<QuizHistoryUiState> = _uiState.asStateFlow()

        private val _uiEffect = Channel<QuizHistoryUiEffect>()
        val uiEffect = _uiEffect.receiveAsFlow()

        init {
            onEvent(QuizHistoryUiEvent.OnLoadQuizHistory)
        }

        /**
         * Handles all UI events
         */
        fun onEvent(event: QuizHistoryUiEvent) {
            when (event) {
                QuizHistoryUiEvent.OnLoadQuizHistory -> loadQuizHistoryMetadata()
                is QuizHistoryUiEvent.OnLoadQuizDetails -> loadQuizDetails(event.sessionId)
                QuizHistoryUiEvent.OnRefreshHistory -> refreshQuizHistory()
                QuizHistoryUiEvent.OnRetryLoadingHistory -> retryLoadingHistory()
                is QuizHistoryUiEvent.OnRetryLoadingDetails -> retryLoadingDetails(event.sessionId)
                QuizHistoryUiEvent.OnClearHistoryError -> clearHistoryError()
                QuizHistoryUiEvent.OnClearDetailsError -> clearDetailsError()
                is QuizHistoryUiEvent.OnNavigateToQuizDetails -> navigateToQuizDetails(event.sessionId)
                QuizHistoryUiEvent.OnNavigateBack -> navigateBack()
                QuizHistoryUiEvent.OnNavigateBackFromDetails -> navigateBackFromDetails()
            }
        }

        private fun loadQuizHistoryMetadata() {
            viewModelScope.launch {
                try {
                    quizRepository.getUserQuizHistoryMetadata().collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        historyState =
                                            _uiState.value.historyState.copy(
                                                isLoading = isLoading,
                                                error = null,
                                            ),
                                    )
                            },
                            onSuccess = { historyList ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        historyState =
                                            _uiState.value.historyState.copy(
                                                isLoading = false,
                                                quizHistory = historyList,
                                                error = null,
                                            ),
                                    )
                            },
                            onError = { message, errorType, _ ->
                                val errorMsg = message ?: "Failed to load quiz history"
                                _uiState.value =
                                    _uiState.value.copy(
                                        historyState =
                                            _uiState.value.historyState.copy(
                                                isLoading = false,
                                                error = errorMsg,
                                                errorType = errorType,
                                            ),
                                    )

                                viewModelScope.launch {
                                    _uiEffect.sendErrorSnackbar(
                                        message = errorMsg,
                                        actionLabel = "Retry",
                                        onActionClick = { onEvent(QuizHistoryUiEvent.OnRetryLoadingHistory) },
                                    )
                                }
                            },
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            historyState =
                                _uiState.value.historyState.copy(
                                    isLoading = false,
                                    error = "An unexpected error occurred: ${e.localizedMessage}",
                                ),
                        )

                    viewModelScope.launch {
                        _uiEffect.sendErrorSnackbar(
                            message = "An unexpected error occurred",
                            actionLabel = "Try Again",
                            onActionClick = { onEvent(QuizHistoryUiEvent.OnRetryLoadingHistory) },
                        )
                    }
                }
            }
        }

        private fun loadQuizDetails(sessionId: String) {
            if (sessionId.isBlank()) {
                viewModelScope.launch {
                    _uiEffect.sendErrorSnackbar(
                        message = "Invalid session ID",
                        actionLabel = null,
                        onActionClick = null,
                    )
                }
                return
            }

            viewModelScope.launch {
                try {
                    _uiEffect.sendInfoSnackbar("Loading quiz details...")

                    quizRepository.getQuizDetailsBySessionId(sessionId).collect { resource ->
                        resource.handle(
                            onLoading = { isLoading ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        detailsState =
                                            _uiState.value.detailsState.copy(
                                                isLoading = isLoading,
                                                error = null,
                                                currentSessionId = sessionId,
                                            ),
                                    )
                            },
                            onSuccess = { quizDetails ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        detailsState =
                                            _uiState.value.detailsState.copy(
                                                isLoading = false,
                                                quizDetails = quizDetails,
                                                error = null,
                                                currentSessionId = sessionId,
                                            ),
                                    )

                                viewModelScope.launch {
                                    _uiEffect.sendSuccessSnackbar("Quiz details loaded successfully")
                                }
                            },
                            onError = { message, errorType, httpCode ->
                                val errorMsg = message ?: "Failed to load quiz details"
                                _uiState.value =
                                    _uiState.value.copy(
                                        detailsState =
                                            _uiState.value.detailsState.copy(
                                                isLoading = false,
                                                error = errorMsg,
                                                errorType = errorType,
                                                currentSessionId = sessionId,
                                            ),
                                    )

                                viewModelScope.launch {
                                    _uiEffect.sendErrorSnackbar(
                                        message = errorMsg,
                                        actionLabel = "Retry",
                                        onActionClick = {
                                            onEvent(QuizHistoryUiEvent.OnRetryLoadingDetails(sessionId))
                                        },
                                    )
                                }
                            },
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            detailsState =
                                _uiState.value.detailsState.copy(
                                    isLoading = false,
                                    error = "Failed to load quiz details: ${e.localizedMessage}",
                                    currentSessionId = sessionId,
                                ),
                        )

                    viewModelScope.launch {
                        _uiEffect.sendErrorSnackbar(
                            message = "Failed to load quiz details",
                            actionLabel = "Try Again",
                            onActionClick = {
                                onEvent(QuizHistoryUiEvent.OnRetryLoadingDetails(sessionId))
                            },
                        )
                    }
                }
            }
        }

        private fun refreshQuizHistory() {
            _uiState.value =
                _uiState.value.copy(
                    historyState = _uiState.value.historyState.copy(isRefreshing = true),
                )

            loadQuizHistoryMetadata()

            viewModelScope.launch {
                kotlinx.coroutines.delay(500)
                _uiState.value =
                    _uiState.value.copy(
                        historyState = _uiState.value.historyState.copy(isRefreshing = false),
                    )
            }
        }

        private fun retryLoadingHistory() {
            loadQuizHistoryMetadata()
        }

        private fun retryLoadingDetails(sessionId: String) {
            loadQuizDetails(sessionId)
        }

        private fun clearHistoryError() {
            _uiState.value =
                _uiState.value.copy(
                    historyState =
                        _uiState.value.historyState.copy(
                            error = null,
                            errorType = null,
                        ),
                )
        }

        private fun clearDetailsError() {
            _uiState.value =
                _uiState.value.copy(
                    detailsState =
                        _uiState.value.detailsState.copy(
                            error = null,
                            errorType = null,
                        ),
                )
        }

        private fun navigateToQuizDetails(sessionId: String) {
            if (sessionId.isBlank()) {
                viewModelScope.launch {
                    _uiEffect.sendErrorSnackbar(
                        message = "Invalid session ID",
                        actionLabel = null,
                        onActionClick = null,
                    )
                }
                return
            }

            viewModelScope.launch {
                _uiEffect.send(QuizHistoryUiEffect.NavigateToQuizDetails(sessionId))
            }
        }

        private fun navigateBack() {
            viewModelScope.launch {
                _uiEffect.send(QuizHistoryUiEffect.NavigateBack)
            }
        }

        private fun navigateBackFromDetails() {
            _uiState.value =
                _uiState.value.copy(
                    detailsState =
                        _uiState.value.detailsState.copy(
                            quizDetails = null,
                            error = null,
                            errorType = null,
                            currentSessionId = null,
                        ),
                )

            viewModelScope.launch {
                _uiEffect.send(QuizHistoryUiEffect.NavigateBackFromDetails)
            }
        }
    }
