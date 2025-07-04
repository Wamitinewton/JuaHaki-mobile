package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizLeaderboardResponse(
    val quizDate: String,
    val quizTitle: String,
    val totalParticipants: Int,
    val topPerformers: List<LeaderboardEntryDto>,
    val userRanking: LeaderboardEntryDto? = null,
    val statistics: QuizStatisticsDto,
)
