package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuestionResultDto(
    val questionNumber: Int,
    val questionText: String,
    val category: String,
    val selectedAnswer: String,
    val correctAnswer: String,
    val selectedOptionText: String,
    val correctOptionText: String,
    val isCorrect: Boolean,
    val explanation: String,
    val timeSpentSeconds: Long?,
)
