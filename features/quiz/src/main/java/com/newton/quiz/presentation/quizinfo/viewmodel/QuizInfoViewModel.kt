package com.newton.quiz.presentation.quizinfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.extensions.sendErrorSnackbar
import com.newton.domain.repository.quiz.QuizRepository
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEffect
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent
import com.newton.quiz.presentation.quizinfo.state.QuizInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizInfoViewModel
@Inject
constructor(
    private val quizRepository: QuizRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizInfoUiState())
    val uiState: StateFlow<QuizInfoUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<QuizInfoUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        onEvent(QuizInfoUiEvent.OnLoadTodaysQuiz)
    }

    /**
     * Handles all UI events
     */
    fun onEvent(event: QuizInfoUiEvent) {
        when (event) {
            QuizInfoUiEvent.OnLoadTodaysQuiz -> loadTodaysQuiz()
            QuizInfoUiEvent.OnRetryLoading -> retryLoading()
            QuizInfoUiEvent.OnClearError -> clearError()
            QuizInfoUiEvent.OnStartQuiz -> startQuiz()
        }
    }

    private fun loadTodaysQuiz() {
        viewModelScope.launch {
            try {
                quizRepository.getTodaysQuiz().collect { resource ->
                    resource.handle(
                        onLoading = { isLoading ->
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = isLoading,
                                    error = null,
                                )
                        },
                        onSuccess = { quizInfo ->
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = false,
                                    quizInfo = quizInfo,
                                    error = null,
                                )
                        },
                        onError = { message, errorType, httpCode ->
                            val errorMsg = message ?: "Failed to load quiz information"
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
                                    onActionClick = { onEvent(QuizInfoUiEvent.OnRetryLoading) },
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
                        onActionClick = { onEvent(QuizInfoUiEvent.OnRetryLoading) },
                    )
                }
            }
        }
    }

    private fun retryLoading() {
        onEvent(QuizInfoUiEvent.OnLoadTodaysQuiz)
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun startQuiz() {
        viewModelScope.launch {
            try {
                quizRepository.startQuiz().collect { resource ->
                    resource.handle(
                        onLoading = { isLoading ->
                            _uiState.value = _uiState.value.copy(isLoading = isLoading)
                        },
                        onSuccess = { quizSession ->
                            quizSession?.let { session ->
                                _uiEffect.send(
                                    QuizInfoUiEffect.NavigateToQuizGame(session.sessionId),
                                )
                            }
                        },
                        onError = { message, errorType, _ ->
                            val errorMsg = message ?: "Failed to start quiz"
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = false,
                                    error = errorMsg,
                                    errorType = errorType,
                                )

                            viewModelScope.launch {
                                _uiEffect.sendErrorSnackbar(
                                    message = errorMsg,
                                    actionLabel = "Try Again",
                                    onActionClick = { startQuiz() },
                                )
                            }
                        },
                    )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to start quiz: ${e.localizedMessage}",
                    )
            }
        }
    }
}
