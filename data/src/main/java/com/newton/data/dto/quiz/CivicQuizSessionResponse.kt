package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class CivicQuizSessionResponse(
    val sessionId: String,
    val currentQuestion: QuizQuestionDto,
    val questionsAnswered: Int,
    val totalQuestions: Int,
    val currentScore: Int,
    val timeElapsed: Long,
    val successful: Boolean = true,
    val errorMessage: String? = null,
)
