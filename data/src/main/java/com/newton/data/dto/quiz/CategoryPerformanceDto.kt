package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class CategoryPerformanceDto(
    val categoryStats: Map<String, CategoryStatsDto>,
    val strongestCategory: String,
    val weakestCategory: String,
    val overallFeedback: String,
)
