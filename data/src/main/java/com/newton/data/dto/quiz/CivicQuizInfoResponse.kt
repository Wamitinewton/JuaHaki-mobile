package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class CivicQuizInfoResponse(
    val quizId: Long,
    val quizDate: String,
    val title: String,
    val description: String,
    val totalQuestions: Int,
    val isActive: Boolean,
    val isExpired: Boolean,
    val expiresAt: String,
    val hasUserAttempted: Boolean,
    val userLastAttempt: UserQuizMetadataDto? = null,
    val timeRemaining: String,
    val participationStats: ParticipationStatsDto,
)
