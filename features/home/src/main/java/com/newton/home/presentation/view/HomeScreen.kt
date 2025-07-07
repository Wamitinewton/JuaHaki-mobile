package com.newton.home.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.newton.commonui.theme.backgroundGradient
import com.newton.home.presentation.view.components.ChatbotCategoriesGrid
import com.newton.home.presentation.view.components.CivicFact
import com.newton.home.presentation.view.components.CivilEducationProgressCard
import com.newton.home.presentation.view.components.CivilEducationStats
import com.newton.home.presentation.view.components.DailyCivicFactCard
import com.newton.home.presentation.view.components.DailyCivicQuizCard
import com.newton.quiz.presentation.quizinfo.state.QuizInfoUiState

@Composable
fun HomeScreen(
    quizInfoUiState: QuizInfoUiState,
    chatbotIconRes: Int = com.newton.commonui.R.drawable.chatbot,
    onNavigateToChat: () -> Unit = {},
    onNavigateToQuizzes: () -> Unit = {},
    onNavigateToDailyCivicQuiz: () -> Unit,
    onNavigateToElectionGuide: () -> Unit = {},
    onNavigateToFinanceBillAnalysis: () -> Unit = {},
    onRetryQuizLoading: () -> Unit = {},
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
) {
    var hidePrompts by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    brush = backgroundGradient(),
                ).pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            hidePrompts = true
                        },
                    )
                },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 16.dp, bottom = 100.dp,),
        ) {
            CivilEducationProgressCard(
                stats = civilEducationStats,
                onCardClick = {
                    onNavigateToQuizzes()
                },
            )

            Spacer(modifier = Modifier.height(24.dp))

            DailyCivicFactCard(
                fact = dailyCivicFact,
            )

            Spacer(modifier = Modifier.height(24.dp))

            DailyCivicQuizCard(
                uiState = quizInfoUiState,
                onCardClick = {
                    onNavigateToDailyCivicQuiz()
                },
                onRetry = {
                    onRetryQuizLoading()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            ChatbotCategoriesGrid(
                onElectionGuideClick = {
                    println("🗳️ Election Guide clicked - Opening documents")
                    onNavigateToElectionGuide()
                },
                onFinanceBillAnalysisClick = {
                    println("💰 Finance Bill Analysis clicked - Opening documents")
                    onNavigateToFinanceBillAnalysis()
                },
            )
        }

//        ChatbotFABContainer(
//            modifier =
//                Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(20.dp),
//            chatbotIconRes = chatbotIconRes,
//            onFabClick = {
//                println("🤖 Chatbot FAB clicked - Opening main chat")
//                onNavigateToChat()
//            },
//            showPrompts = !hidePrompts,
//            autoHidePromptsDuration = 4000L,
//        )
    }
}