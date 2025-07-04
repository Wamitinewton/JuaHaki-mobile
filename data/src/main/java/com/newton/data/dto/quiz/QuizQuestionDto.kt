package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestionDto(
    val questionId: Long,
    val questionNumber: Int,
    val questionText: String,
    val category: String,
    val difficulty: String,
    val options: List<QuizOptionDto>,
    val sourceReference: String,
)
