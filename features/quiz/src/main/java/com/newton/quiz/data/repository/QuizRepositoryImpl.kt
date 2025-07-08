package com.newton.quiz.data.repository

import com.newton.core.enums.ErrorType
import com.newton.core.utils.DateUtils.toApiString
import com.newton.core.utils.Resource
import com.newton.data.mappers.toAnswerResult
import com.newton.data.mappers.toQuizInfo
import com.newton.data.mappers.toQuizLeaderboard
import com.newton.data.mappers.toQuizQuestion
import com.newton.data.mappers.toQuizSession
import com.newton.data.mappers.toQuizStatistics
import com.newton.data.mappers.toSubmitRequest
import com.newton.data.mappers.toUserQuizSummary
import com.newton.data.remote.QuizApiService
import com.newton.data.remote.utils.safeApiCall
import com.newton.domain.models.quiz.AnswerResult
import com.newton.domain.models.quiz.AnswerSubmission
import com.newton.domain.models.quiz.QuizInfo
import com.newton.domain.models.quiz.QuizLeaderboard
import com.newton.domain.models.quiz.QuizSession
import com.newton.domain.models.quiz.QuizStatistics
import com.newton.domain.models.quiz.UserQuizSummary
import com.newton.domain.repository.quiz.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class QuizRepositoryImpl
    @Inject
    constructor(
        private val quizApiService: QuizApiService,
    ) : QuizRepository {
        override suspend fun getTodaysQuiz(): Flow<Resource<QuizInfo>> =
            safeApiCall(
                apiCall = { quizApiService.getTodaysQuiz() },
                errorHandler = { "Failed to get today's quiz: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { civicQuizInfoResponse ->
                            Resource.Success(civicQuizInfoResponse.toQuizInfo())
                        } ?: Resource.Error("No Quiz data available")
                    }

                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun getQuizInfo(date: LocalDate): Flow<Resource<QuizInfo>> =
            safeApiCall(
                apiCall = { quizApiService.getQuizInfo(date.toApiString()) },
                errorHandler = { "Failed to get quiz info: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { civicQuizInfoResponse ->
                            Resource.Success(civicQuizInfoResponse.toQuizInfo())
                        } ?: Resource.Error("No quiz data available")
                    }

                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun startQuiz(): Flow<Resource<QuizSession>> =
            safeApiCall(
                apiCall = { quizApiService.startQuiz() },
                errorHandler = { "Failed to start quiz: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { startQuizResponse ->
                            if (startQuizResponse.successful) {
                                Resource.Success(startQuizResponse.toQuizSession())
                            } else {
                                Resource.Error(startQuizResponse.errorMessage ?: "Failed to start quiz")
                            }
                        } ?: Resource.Error("No response data")
                    }

                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun submitAnswer(answerSubmission: AnswerSubmission): Flow<Resource<AnswerResult>> =
            safeApiCall(
                apiCall = { quizApiService.submitAnswer(answerSubmission.toSubmitRequest()) },
                errorHandler = { "Failed to submit answer: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { submitResponse ->
                            if (submitResponse.successful) {
                                Resource.Success(submitResponse.toAnswerResult())
                            } else {
                                Resource.Error(submitResponse.errorMessage ?: "Failed to submit answer")
                            }
                        } ?: Resource.Error("No response data")
                    }

                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun getSessionStatus(sessionId: String): Flow<Resource<QuizSession>> =
            safeApiCall(
                apiCall = { quizApiService.getSessionStatus(sessionId) },
                errorHandler = { "Failed to get session status: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { sessionResponse ->
                            if (sessionResponse.successful) {
                                Resource.Success(
                                    QuizSession(
                                        sessionId = sessionResponse.sessionId,
                                        quizId = 0L,
                                        title = "",
                                        totalQuestions = sessionResponse.totalQuestions,
                                        currentQuestion = sessionResponse.currentQuestion.toQuizQuestion(),
                                    ),
                                )
                            } else {
                                Resource.Error(
                                    sessionResponse.errorMessage ?: "Failed to get session status",
                                )
                            }
                        } ?: Resource.Error("No response data")
                    }

                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun abandonSession(sessionId: String): Flow<Resource<Unit>> =
            safeApiCall(
                apiCall = { quizApiService.abandonSession(sessionId) },
                errorHandler = { "Failed to abandon session: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(Unit)
                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun getQuizResults(sessionId: String): Flow<Resource<UserQuizSummary>> =
            safeApiCall(
                apiCall = { quizApiService.getQuizResults(sessionId) },
                errorHandler = { "Failed to get quiz results: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { quizSummary ->
                            Resource.Success(quizSummary.toUserQuizSummary())
                        } ?: Resource.Error("No quiz results available")
                    }

                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun getUserQuizHistoryMetadata(): Flow<Resource<List<UserQuizSummary>>> =
            safeApiCall(
                apiCall = { quizApiService.getUserQuizHistoryMetadata() },
                errorHandler = { "Failed to get quiz history: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { historyList ->
                            Resource.Success(historyList.map { it.toUserQuizSummary() })
                        } ?: Resource.Error("No quiz history available")
                    }

                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun getQuizDetailsBySessionId(sessionId: String): Flow<Resource<UserQuizSummary>> =
            safeApiCall(
                apiCall = { quizApiService.getQuizDetailsBySessionId(sessionId) },
                errorHandler = { "Failed to get quiz details: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { quizSummary ->
                            Resource.Success(quizSummary.toUserQuizSummary())
                        } ?: Resource.Error("No quiz details available")
                    }

                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun getTodaysLeaderboard(): Flow<Resource<QuizLeaderboard>> =
            safeApiCall(
                apiCall = { quizApiService.getTodaysLeaderboard() },
                errorHandler = { "Failed to get leaderboard: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { leaderboard ->
                            Resource.Success(leaderboard.toQuizLeaderboard())
                        } ?: Resource.Error("No leaderboard data available")
                    }
                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun getLeaderboard(date: LocalDate): Flow<Resource<QuizLeaderboard>> =
            safeApiCall(
                apiCall = { quizApiService.getLeaderboard(date.toApiString()) },
                errorHandler = { "Failed to get leaderboard: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { leaderboard ->
                            Resource.Success(leaderboard.toQuizLeaderboard())
                        } ?: Resource.Error("No leaderboard data available")
                    }

                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun getQuizStatistics(date: LocalDate): Flow<Resource<QuizStatistics>> =
            safeApiCall(
                apiCall = { quizApiService.getQuizStatistics(date.toApiString()) },
                errorHandler = { "Failed to get quiz statistics: ${it.localizedMessage}" },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.data?.let { statistics ->
                            Resource.Success(statistics.toQuizStatistics())
                        } ?: Resource.Error("No statistics available")
                    }

                    is Resource.Error ->
                        Resource.Error(
                            resource.message ?: "Unknown error",
                            resource.errorType ?: ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )

                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }
    }
