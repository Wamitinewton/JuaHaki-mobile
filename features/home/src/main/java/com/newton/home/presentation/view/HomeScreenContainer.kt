package com.newton.home.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.newton.home.presentation.view.components.CivicFact
import com.newton.home.presentation.view.components.CivilEducationStats
import com.newton.quiz.presentation.quizinfo.event.QuizInfoUiEvent
import com.newton.quiz.presentation.quizinfo.viewmodel.QuizInfoViewModel

@Composable
fun HomeScreenContainer(
    onNavigateToChat: () -> Unit = {},
    onNavigateToQuizzes: () -> Unit = {},
    onNavigateToDailyCivicQuiz: () -> Unit,
    onNavigateToElectionGuide: () -> Unit = {},
    onNavigateToFinanceBillAnalysis: () -> Unit = {},
    chatbotIconRes: Int = com.newton.commonui.R.drawable.chatbot,
    civilEducationStats: CivilEducationStats =
        CivilEducationStats(
            dailyQuizzesCompleted = 3,
            totalQuizzesAvailable = 10,
            averageScore = 0.85f,
            currentStreak = 7,
            totalPoints = 1250,
            level = "Intermediate",
            progressToNextLevel = 0.65f,
        ),
    dailyCivicFact: CivicFact =
        CivicFact(
            fact = "Every Kenyan citizen has the right to clean and safe water in adequate quantities, and reasonable standards of sanitation.",
            source = "Article 43(1)(d), Constitution of Kenya 2010",
            category = "Economic & Social Rights",
        ),
    viewModel: QuizInfoViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        quizInfoUiState = uiState,
        chatbotIconRes = chatbotIconRes,
        onNavigateToChat = onNavigateToChat,
        onNavigateToQuizzes = onNavigateToQuizzes,
        onNavigateToDailyCivicQuiz = onNavigateToDailyCivicQuiz,
        onNavigateToElectionGuide = onNavigateToElectionGuide,
        onNavigateToFinanceBillAnalysis = onNavigateToFinanceBillAnalysis,
        onRetryQuizLoading = {
            viewModel.onEvent(QuizInfoUiEvent.OnRetryLoading)
        },
        civilEducationStats = civilEducationStats,
        dailyCivicFact = dailyCivicFact,
    )
}