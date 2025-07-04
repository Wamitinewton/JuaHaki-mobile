package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class CategoryStatsDto(
    val category: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val percentage: Double,
    val performance: String,
    val feedback: String,
)
