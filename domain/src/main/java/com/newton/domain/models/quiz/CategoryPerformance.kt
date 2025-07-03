package com.newton.domain.models.quiz

data class CategoryPerformance(
    val categoryStats: Map<String, CategoryStats>,
    val strongestCategory: String,
    val weakestCategory: String,
    val overallFeedback: String
)