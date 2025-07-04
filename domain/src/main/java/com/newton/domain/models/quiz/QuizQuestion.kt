package com.newton.domain.models.quiz

data class QuizQuestion(
    val questionId: Long,
    val questionNumber: Int,
    val questionText: String,
    val category: String,
    val difficulty: String,
    val options: List<QuizOption>,
    val sourceReference: String,
)
