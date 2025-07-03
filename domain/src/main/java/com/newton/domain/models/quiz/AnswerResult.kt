package com.newton.domain.models.quiz

data class AnswerResult(
    val correct: Boolean,
    val message: String,
    val correctAnswer: String,
    val correctOptionText: String,
    val explanation: String,
    val currentScore: Int,
    val questionsAnswered: Int,
    val totalQuestions: Int,
    val hasNextQuestion: Boolean,
    val nextQuestion: QuizQuestion? = null,
    val finalResults: UserQuizSummary? = null
)