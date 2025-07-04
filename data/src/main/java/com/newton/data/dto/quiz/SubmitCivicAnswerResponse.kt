package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class SubmitCivicAnswerResponse(
    val correct: Boolean,
    val message: String,
    val correctAnswer: String,
    val correctOptionText: String,
    val explanation: String,
    val currentScore: Int,
    val questionsAnswered: Int,
    val totalQuestions: Int,
    val hasNextQuestion: Boolean,
    val nextQuestion: QuizQuestionDto? = null,
    val finalResults: UserQuizSummaryDto? = null,
    val successful: Boolean = true,
    val errorMessage: String? = null,
)
