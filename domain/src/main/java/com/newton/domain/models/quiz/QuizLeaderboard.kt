package com.newton.domain.models.quiz

data class QuizLeaderboard(
    val quizDate: String,
    val quizTitle: String,
    val totalParticipants: Int,
    val topPerformers: List<LeaderboardEntry>,
    val userRanking: LeaderboardEntry? = null,
    val statistics: QuizStatistics
)