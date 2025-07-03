package com.newton.domain.models.quiz

data class QuizSession(
    val sessionId: String,
    val quizId: Long,
    val title: String,
    val totalQuestions: Int,
    val currentQuestion: QuizQuestion
)