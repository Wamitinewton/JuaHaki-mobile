package com.newton.data.mappers

import com.newton.data.dto.quiz.CategoryPerformanceDto
import com.newton.data.dto.quiz.CategoryStatsDto
import com.newton.data.dto.quiz.CivicQuizInfoResponse
import com.newton.data.dto.quiz.LeaderboardEntryDto
import com.newton.data.dto.quiz.QuestionResultDto
import com.newton.data.dto.quiz.QuizLeaderboardResponse
import com.newton.data.dto.quiz.QuizOptionDto
import com.newton.data.dto.quiz.QuizQuestionDto
import com.newton.data.dto.quiz.QuizStatisticsDto
import com.newton.data.dto.quiz.StartCivicQuizResponse
import com.newton.data.dto.quiz.SubmitCivicAnswerRequest
import com.newton.data.dto.quiz.SubmitCivicAnswerResponse
import com.newton.data.dto.quiz.UserQuizMetadataDto
import com.newton.data.dto.quiz.UserQuizSummaryDto
import com.newton.domain.models.quiz.AnswerResult
import com.newton.domain.models.quiz.AnswerSubmission
import com.newton.domain.models.quiz.CategoryPerformance
import com.newton.domain.models.quiz.CategoryStats
import com.newton.domain.models.quiz.LeaderboardEntry
import com.newton.domain.models.quiz.QuestionResult
import com.newton.domain.models.quiz.QuizInfo
import com.newton.domain.models.quiz.QuizLeaderboard
import com.newton.domain.models.quiz.QuizOption
import com.newton.domain.models.quiz.QuizQuestion
import com.newton.domain.models.quiz.QuizSession
import com.newton.domain.models.quiz.QuizStatistics
import com.newton.domain.models.quiz.UserQuizSummary

fun CivicQuizInfoResponse.toQuizInfo(): QuizInfo =
    QuizInfo(
        quizId = quizId,
        quizDate = quizDate,
        title = title,
        description = description,
        totalQuestions = totalQuestions,
        isActive = active,
        isExpired = expired,
        expiresAt = expiresAt,
        hasUserAttempted = hasUserAttempted,
        userLastAttempt = userLastAttempt?.toUserQuizSummary(),
        timeRemaining = timeRemaining,
    )

fun StartCivicQuizResponse.toQuizSession(): QuizSession =
    QuizSession(
        sessionId = sessionId,
        quizId = quizId,
        title = title,
        totalQuestions = totalQuestions,
        currentQuestion = currentQuestion.toQuizQuestion(),
    )

fun QuizQuestionDto.toQuizQuestion(): QuizQuestion =
    QuizQuestion(
        questionId = questionId,
        questionNumber = questionNumber,
        questionText = questionText,
        category = category,
        difficulty = difficulty,
        options = options.map { it.toQuizOption() },
        sourceReference = sourceReference,
    )

fun QuizOptionDto.toQuizOption(): QuizOption =
    QuizOption(
        optionLetter = optionLetter,
        optionText = optionText,
    )

fun SubmitCivicAnswerResponse.toAnswerResult(): AnswerResult =
    AnswerResult(
        correct = correct,
        message = message,
        correctAnswer = correctAnswer,
        correctOptionText = correctOptionText,
        explanation = explanation,
        currentScore = currentScore,
        questionsAnswered = questionsAnswered,
        totalQuestions = totalQuestions,
        hasNextQuestion = hasNextQuestion,
        nextQuestion = nextQuestion?.toQuizQuestion(),
        finalResults = finalResults?.toUserQuizSummary(),
    )

fun AnswerSubmission.toSubmitRequest(): SubmitCivicAnswerRequest =
    SubmitCivicAnswerRequest(
        sessionId = sessionId,
        answer = answer,
        timeSpentSeconds = timeSpentSeconds,
    )

fun UserQuizSummaryDto.toUserQuizSummary(): UserQuizSummary =
    UserQuizSummary(
        sessionId = sessionId,
        attemptId = attemptId,
        quizTitle = quizTitle,
        totalQuestions = totalQuestions,
        questionsAnswered = questionsAnswered,
        correctAnswers = correctAnswers,
        score = score,
        performanceLevel = performanceLevel,
        startedAt = startedAt,
        completedAt = completedAt,
        durationSeconds = durationSeconds,
        durationFormatted = durationFormatted,
        questionResults = questionResults.map { it.toQuestionResult() },
        categoryPerformance = categoryPerformance.toCategoryPerformance(),
        completionMessage = completionMessage,
    )

fun UserQuizMetadataDto.toUserQuizSummary(): UserQuizSummary =
    UserQuizSummary(
        sessionId = sessionId,
        attemptId = attemptId,
        quizTitle = "",
        totalQuestions = 0,
        questionsAnswered = 0,
        correctAnswers = 0,
        score = score,
        performanceLevel = performanceLevel,
        startedAt = "",
        completedAt = completedAt,
        durationSeconds = durationSeconds,
        durationFormatted = durationFormatted,
        questionResults = emptyList(),
        categoryPerformance =
            CategoryPerformance(
                categoryStats = emptyMap(),
                strongestCategory = "",
                weakestCategory = "",
                overallFeedback = "",
            ),
        completionMessage = "",
    )

fun QuestionResultDto.toQuestionResult(): QuestionResult =
    QuestionResult(
        questionNumber = questionNumber,
        questionText = questionText,
        category = category,
        selectedAnswer = selectedAnswer,
        correctAnswer = correctAnswer,
        selectedOptionText = selectedOptionText,
        correctOptionText = correctOptionText,
        isCorrect = correct,
        explanation = explanation,
        timeSpentSeconds = timeSpentSeconds,
    )

fun CategoryPerformanceDto.toCategoryPerformance(): CategoryPerformance =
    CategoryPerformance(
        categoryStats = categoryStats.mapValues { it.value.toCategoryStats() },
        strongestCategory = strongestCategory,
        weakestCategory = weakestCategory,
        overallFeedback = overallFeedback,
    )

fun CategoryStatsDto.toCategoryStats(): CategoryStats =
    CategoryStats(
        category = category,
        totalQuestions = totalQuestions,
        correctAnswers = correctAnswers,
        percentage = percentage,
        performance = performance,
        feedback = feedback,
    )

fun QuizLeaderboardResponse.toQuizLeaderboard(): QuizLeaderboard =
    QuizLeaderboard(
        quizDate = quizDate,
        quizTitle = quizTitle,
        totalParticipants = totalParticipants,
        topPerformers = topPerformers.map { it.toLeaderboardEntry() },
        userRanking = userRanking?.toLeaderboardEntry(),
        statistics = statistics.toQuizStatistics(),
    )

fun LeaderboardEntryDto.toLeaderboardEntry(): LeaderboardEntry =
    LeaderboardEntry(
        rank = rank,
        username = username,
        firstName = firstName,
        score = score,
        performanceLevel = performanceLevel,
        completedAt = completedAt,
        durationSeconds = durationSeconds,
        durationFormatted = durationFormatted,
        isCurrentUser = isCurrentUser,
    )

fun QuizStatisticsDto.toQuizStatistics(): QuizStatistics =
    QuizStatistics(
        totalAttempts = totalAttempts,
        completedAttempts = completedAttempts,
        averageScore = averageScore,
        completionRate = completionRate,
        mostDifficultQuestion = mostDifficultQuestion,
        easiestQuestion = easiestQuestion,
        popularCategory = popularCategory,
    )
