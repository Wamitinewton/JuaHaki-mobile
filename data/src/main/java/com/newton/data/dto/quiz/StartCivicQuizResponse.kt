package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class StartCivicQuizResponse(
    val sessionId: String,
    val quizId: Long,
    val title: String,
    val totalQuestions: Int,
    val currentQuestion: QuizQuestionDto,
    val successful: Boolean = true,
    val errorMessage: String? = null,
)
