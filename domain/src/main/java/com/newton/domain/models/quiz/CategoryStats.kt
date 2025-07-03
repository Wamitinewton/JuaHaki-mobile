package com.newton.domain.models.quiz

data class CategoryStats(
    val category: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val percentage: Double,
    val performance: String,
    val feedback: String
)