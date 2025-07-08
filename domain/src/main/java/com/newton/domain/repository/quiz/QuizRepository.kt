package com.newton.domain.repository.quiz

import com.newton.core.utils.Resource
import com.newton.domain.models.quiz.AnswerResult
import com.newton.domain.models.quiz.AnswerSubmission
import com.newton.domain.models.quiz.QuizInfo
import com.newton.domain.models.quiz.QuizLeaderboard
import com.newton.domain.models.quiz.QuizSession
import com.newton.domain.models.quiz.QuizStatistics
import com.newton.domain.models.quiz.UserQuizSummary
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface QuizRepository {
    /**
     * Get today's quiz information
     */
    suspend fun getTodaysQuiz(): Flow<Resource<QuizInfo>>

    /**
     * Get quiz information for a specific date
     */
    suspend fun getQuizInfo(date: LocalDate): Flow<Resource<QuizInfo>>

    /**
     * Start a new quiz session
     */
    suspend fun startQuiz(): Flow<Resource<QuizSession>>

    /**
     * Submit an answer to the current question
     */
    suspend fun submitAnswer(answerSubmission: AnswerSubmission): Flow<Resource<AnswerResult>>

    /**
     * Get the current status of a quiz session
     */
    suspend fun getSessionStatus(sessionId: String): Flow<Resource<QuizSession>>

    /**
     * Abandon/cancel a quiz session
     */
    suspend fun abandonSession(sessionId: String): Flow<Resource<Unit>>

    /**
     * Get complete quiz results for a session
     */
    suspend fun getQuizResults(sessionId: String): Flow<Resource<UserQuizSummary>>

    /**
     * Get user's quiz history metadata (list of past attempts)
     */
    suspend fun getUserQuizHistoryMetadata(): Flow<Resource<List<UserQuizSummary>>>

    /**
     * Get detailed quiz results by session ID
     */
    suspend fun getQuizDetailsBySessionId(sessionId: String): Flow<Resource<UserQuizSummary>>

    /**
     * Get today's leaderboard
     */
    suspend fun getTodaysLeaderboard(): Flow<Resource<QuizLeaderboard>>

    /**
     * Get leaderboard for a specific date
     */
    suspend fun getLeaderboard(date: LocalDate): Flow<Resource<QuizLeaderboard>>

    /**
     * Get quiz statistics for a specific date
     */
    suspend fun getQuizStatistics(date: LocalDate): Flow<Resource<QuizStatistics>>
}
