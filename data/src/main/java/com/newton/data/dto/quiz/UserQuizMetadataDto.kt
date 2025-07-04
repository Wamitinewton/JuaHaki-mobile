package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class UserQuizMetadataDto(
    val sessionId: String,
    val attemptId: Long,
    val score: Int,
    val performanceLevel: String,
    val completedAt: String,
    val durationSeconds: Long? = null,
    val durationFormatted: String,
)
