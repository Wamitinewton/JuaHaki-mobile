package com.newton.data.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizOptionDto(
    val optionLetter: String,
    val optionText: String,
)
