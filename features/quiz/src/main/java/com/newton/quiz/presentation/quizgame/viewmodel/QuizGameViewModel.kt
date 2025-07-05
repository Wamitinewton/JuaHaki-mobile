package com.newton.quiz.presentation.quizgame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.extensions.sendErrorSnackbar
import com.newton.core.extensions.sendInfoSnackbar
import com.newton.core.extensions.sendWarningSnackbar
import com.newton.domain.models.quiz.AnswerSubmission
import com.newton.domain.repository.quiz.QuizRepository
import com.newton.quiz.presentation.quizgame.event.QuizGameUiEffect
import com.newton.quiz.presentation.quizgame.event.QuizGameUiEvent
import com.newton.quiz.presentation.quizgame.state.QuizGameUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizGameViewModel
@Inject
constructor(
    private val quizRepository: QuizRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizGameUiState())
    val uiState: StateFlow<QuizGameUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<QuizGameUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private var timerJob: Job? = null
    private var currentSessionId: String? = null

    fun onEvent(event: QuizGameUiEvent) {
        when (event) {
            is QuizGameUiEvent.OnInitializeQuiz -> event.sessionId?.let { initializeQuiz(it) }
            is QuizGameUiEvent.OnSelectAnswer -> selectAnswer(event.answer)
            QuizGameUiEvent.OnSubmitAnswer -> submitAnswer()
            QuizGameUiEvent.OnNextQuestion -> nextQuestion()
            QuizGameUiEvent.OnAbandonQuiz -> showAbandonConfirmation()
            QuizGameUiEvent.OnClearError -> clearError()
            QuizGameUiEvent.OnNavigateToResults -> navigateToResults()
            QuizGameUiEvent.OnNavigateBack -> navigateBack()
        }
    }

    private fun initializeQuiz(sessionId: String) {
        if (sessionId.isBlank()) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = "Invalid session ID provided"
            )
            return
        }

        currentSessionId = sessionId
        getSessionStatus(sessionId)
    }

    private fun getSessionStatus(sessionId: String) {
        viewModelScope.launch {
            try {
                _uiEffect.sendInfoSnackbar("Loading quiz session...")

                quizRepository.getSessionStatus(sessionId).collect { resource ->
                    resource.handle(
                        onLoading = { isLoading ->
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = isLoading,
                                    error = null,
                                )
                        },
                        onSuccess = { quizSession ->
                            quizSession?.let { session ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        currentSession = session,
                                        currentQuestionNumber = session.currentQuestion?.questionNumber,
                                        error = null,
                                    )
                                startTimer()
                            } ?: run {
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    error = "Quiz session not found"
                                )
                            }
                        },
                        onError = { message, _, _ ->
                            val errorMsg = message ?: "Failed to load quiz session"
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = false,
                                    error = errorMsg,
                                )

                            viewModelScope.launch {
                                _uiEffect.sendErrorSnackbar(
                                    message = errorMsg,
                                    actionLabel = "Retry",
                                    onActionClick = { getSessionStatus(sessionId) },
                                )
                            }
                        },
                    )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to load session: ${e.localizedMessage}",
                    )

                viewModelScope.launch {
                    _uiEffect.sendErrorSnackbar(
                        message = "Failed to load quiz session",
                        actionLabel = "Try Again",
                        onActionClick = { getSessionStatus(sessionId) },
                    )
                }
            }
        }
    }

    private fun submitAnswer() {
        val currentState = _uiState.value
        val sessionId = currentSessionId
        val answer = currentState.currentAnswer

        if (sessionId.isNullOrBlank()) {
            viewModelScope.launch {
                _uiEffect.sendErrorSnackbar("No active quiz session")
            }
            return
        }

        if (answer.isEmpty()) {
            viewModelScope.launch {
                _uiEffect.sendWarningSnackbar("Please select an answer")
            }
            return
        }

        val answerSubmission =
            AnswerSubmission(
                sessionId = sessionId,
                answer = answer,
                timeSpentSeconds = currentState.timeSpent,
            )

        viewModelScope.launch {
            try {
                _uiEffect.sendInfoSnackbar("Submitting your answer...")

                quizRepository.submitAnswer(answerSubmission).collect { resource ->
                    resource.handle(
                        onLoading = { isLoading ->
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = isLoading,
                                    error = null,
                                )
                        },
                        onSuccess = { answerResult ->
                            answerResult?.let { result ->
                                stopTimer()
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        showExplanation = true,
                                        answerResult = result,
                                        score = result.currentScore,
                                        error = null,
                                    )
                            }
                        },
                        onError = { message, _, _ ->
                            val errorMsg = message ?: "Failed to submit answer"
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = false,
                                    error = errorMsg,
                                )

                            viewModelScope.launch {
                                _uiEffect.sendErrorSnackbar(
                                    message = errorMsg,
                                    actionLabel = "Try Again",
                                    onActionClick = { submitAnswer() },
                                )
                            }
                        },
                    )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to submit answer: ${e.localizedMessage}",
                    )

                viewModelScope.launch {
                    _uiEffect.sendErrorSnackbar(
                        message = "Failed to submit answer",
                        actionLabel = "Try Again",
                        onActionClick = { submitAnswer() },
                    )
                }
            }
        }
    }

    private fun nextQuestion() {
        val answerResult = _uiState.value.answerResult ?: return

        if (answerResult.hasNextQuestion && answerResult.nextQuestion != null) {
            _uiState.value =
                _uiState.value.copy(
                    currentSession =
                        _uiState.value.currentSession?.copy(
                            currentQuestion = answerResult.nextQuestion,
                        ),
                    currentQuestionNumber = answerResult.nextQuestion?.questionNumber,
                    currentAnswer = "",
                    showExplanation = false,
                    answerResult = null,
                    timeSpent = 0,
                    error = null,
                )
            startTimer()
        } else {
            currentSessionId?.let { sessionId ->
                viewModelScope.launch {
                    _uiEffect.send(QuizGameUiEffect.NavigateToResults(sessionId))
                }
            }
        }
    }

    private fun showAbandonConfirmation() {
        viewModelScope.launch {
            _uiEffect.send(QuizGameUiEffect.ShowAbandonConfirmationDialog)
        }
    }

    fun confirmAbandonQuiz() {
        currentSessionId?.let { sessionId ->
            viewModelScope.launch {
                try {
                    quizRepository.abandonSession(sessionId).collect { resource ->
                        resource.handle(
                            onSuccess = {
                                stopTimer()
                                _uiEffect.send(QuizGameUiEffect.NavigateBack)
                            },
                            onError = { message, _, _ ->
                                val errorMsg = message ?: "Failed to abandon session"
                                _uiEffect.sendErrorSnackbar(errorMsg)
                            },
                        )
                    }
                } catch (e: Exception) {
                    _uiEffect.sendErrorSnackbar("Failed to abandon quiz")
                }
            }
        }
        stopTimer()
    }

    private fun selectAnswer(answer: String) {
        _uiState.value =
            _uiState.value.copy(
                currentAnswer = answer,
                error = null,
            )
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun navigateToResults() {
        currentSessionId?.let { sessionId ->
            viewModelScope.launch {
                _uiEffect.send(QuizGameUiEffect.NavigateToResults(sessionId))
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEffect.send(QuizGameUiEffect.NavigateBack)
        }
    }

    private fun startTimer() {
        stopTimer()
        timerJob =
            viewModelScope.launch {
                while (true) {
                    delay(1000)
                    _uiState.value =
                        _uiState.value.copy(
                            timeSpent = _uiState.value.timeSpent + 1,
                        )
                }
            }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}