package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class UserQuizSummaryDto(
    val sessionId: String,
    val attemptId: Long,
    val quizTitle: String,
    val totalQuestions: Int,
    val questionsAnswered: Int,
    val correctAnswers: Int,
    val score: Int,
    val performanceLevel: String,
    val startedAt: String,
    val completedAt: String? = null,
    val durationSeconds: Long? = null,
    val durationFormatted: String,
    val questionResults: List<QuestionResultDto>,
    val categoryPerformance: CategoryPerformanceDto,
    val completionMessage: String,
)
