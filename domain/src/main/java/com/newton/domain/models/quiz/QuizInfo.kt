package com.newton.domain.models.quiz

data class QuizInfo(
    val quizId: Long,
    val quizDate: String,
    val title: String,
    val description: String,
    val totalQuestions: Int,
    val isActive: Boolean,
    val isExpired: Boolean,
    val expiresAt: String,
    val hasUserAttempted: Boolean,
    val userLastAttempt: UserQuizSummary? = null,
    val timeRemaining: String,
)
