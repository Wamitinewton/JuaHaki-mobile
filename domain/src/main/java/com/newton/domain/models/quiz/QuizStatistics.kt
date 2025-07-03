package com.newton.domain.models.quiz

data class QuizStatistics(
    val totalAttempts: Int,
    val completedAttempts: Int,
    val averageScore: Double,
    val completionRate: Double,
    val mostDifficultQuestion: String,
    val easiestQuestion: String,
    val popularCategory: String
)