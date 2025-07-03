package com.newton.core.enums

enum class QuizPerformanceLevel(val displayName: String, val color: androidx.compose.ui.graphics.Color) {
    EXCELLENT("Excellent", androidx.compose.ui.graphics.Color(0xFF4CAF50)),
    GOOD("Good", androidx.compose.ui.graphics.Color(0xFF8BC34A)),
    FAIR("Fair", androidx.compose.ui.graphics.Color(0xFFFF9800)),
    NEEDS_IMPROVEMENT("Needs Improvement", androidx.compose.ui.graphics.Color(0xFFF44336))
}