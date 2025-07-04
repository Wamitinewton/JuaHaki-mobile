package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class SubmitCivicAnswerRequest(
    val sessionId: String,
    val answer: String,
    val timeSpentSeconds: Long? = null,
)
