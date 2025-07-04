package com.newton.domain.models.quiz

data class AnswerSubmission(
    val sessionId: String,
    val answer: String,
    val timeSpentSeconds: Long? = null,
)
