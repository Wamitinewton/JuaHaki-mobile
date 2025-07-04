package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class ParticipationStatsDto(
    val totalAttempts: Int,
    val averageScore: Double,
    val completedAttempts: Int,
)
