package com.newton.domain.models.quiz

data class LeaderboardEntry(
    val rank: Int,
    val username: String,
    val firstName: String,
    val score: Int,
    val performanceLevel: String,
    val completedAt: String,
    val durationSeconds: Long?,
    val durationFormatted: String,
    val isCurrentUser: Boolean
)