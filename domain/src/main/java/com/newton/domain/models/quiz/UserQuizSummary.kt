package com.newton.domain.models.quiz

data class UserQuizSummary(
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
    val questionResults: List<QuestionResult>,
    val categoryPerformance: CategoryPerformance,
    val completionMessage: String,
)
