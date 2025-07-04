package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardEntryDto(
    val rank: Int,
    val username: String,
    val firstName: String,
    val score: Int,
    val performanceLevel: String,
    val completedAt: String,
    val durationSeconds: Long?,
    val durationFormatted: String,
    val isCurrentUser: Boolean,
)
