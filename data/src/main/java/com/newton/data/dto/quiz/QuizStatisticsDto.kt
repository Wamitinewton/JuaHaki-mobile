package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizStatisticsDto(
    val totalAttempts: Int,
    val completedAttempts: Int,
    val averageScore: Double,
    val completionRate: Double,
    val mostDifficultQuestion: String,
    val easiestQuestion: String,
    val popularCategory: String,
)
