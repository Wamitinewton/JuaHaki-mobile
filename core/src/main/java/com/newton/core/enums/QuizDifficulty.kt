package com.newton.core.enums

enum class QuizDifficulty(val displayName: String, val color: androidx.compose.ui.graphics.Color) {
    EASY("Easy", androidx.compose.ui.graphics.Color(0xFF4CAF50)),
    MEDIUM("Medium", androidx.compose.ui.graphics.Color(0xFFFF9800)),
    HARD("Hard", androidx.compose.ui.graphics.Color(0xFFF44336))
}