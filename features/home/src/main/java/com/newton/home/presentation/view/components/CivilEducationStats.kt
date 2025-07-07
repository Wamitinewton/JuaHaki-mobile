package com.newton.home.presentation.view.components

data class CivilEducationStats(
    val dailyQuizzesCompleted: Int = 0,
    val totalQuizzesAvailable: Int = 10,
    val averageScore: Float = 0f,
    val currentStreak: Int = 0,
    val totalPoints: Int = 0,
    val level: String = "Beginner",
    val progressToNextLevel: Float = 0f,
)
