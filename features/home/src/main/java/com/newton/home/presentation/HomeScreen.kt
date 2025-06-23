package com.newton.home.presentation

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.newton.commonui.components.ChatbotFABContainer
import com.newton.commonui.theme.backgroundGradient
import com.newton.home.presentation.userstats.view.CivilEducationStats
import com.newton.home.presentation.userstats.view.components.ChatbotCategoriesGrid
import com.newton.home.presentation.userstats.view.components.CivicQuizData
import com.newton.home.presentation.userstats.view.components.CivilEducationProgressCard
import com.newton.home.presentation.userstats.view.components.DailyCivicQuizCard
import com.newton.home.presentation.userstats.view.components.DailyCivicFactCard
import com.newton.home.presentation.userstats.view.components.CivicFact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    chatbotIconRes: Int = com.newton.commonui.R.drawable.chatbot,
    onNavigateToChat: () -> Unit = {},
    onNavigateToQuizzes: () -> Unit = {},
    onNavigateToDailyCivicQuiz: () -> Unit = {},
    onNavigateToElectionGuide: () -> Unit = {},
    onNavigateToFinanceBillAnalysis: () -> Unit = {},
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
    dailyCivicQuizData: CivicQuizData =
        CivicQuizData(
            title = "Daily Civic Quiz",
            subtitle = "Learn your constitutional rights",
            totalAttempts = 1247,
            averageScore = 78f,
            todaysQuestions = 5,
            isCompleted = false
        ),
    dailyCivicFact: CivicFact =
        CivicFact(
            fact = "Every Kenyan citizen has the right to clean and safe water in adequate quantities, and reasonable standards of sanitation.",
            source = "Article 43(1)(d), Constitution of Kenya 2010",
            category = "Economic & Social Rights"
        )
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
                    .padding(top = 16.dp, bottom = 100.dp),
        ) {
            CivilEducationProgressCard(
                stats = civilEducationStats,
                onCardClick = {
                    onNavigateToQuizzes()
                },
            )

            Spacer(modifier = Modifier.height(24.dp))

            DailyCivicFactCard(
                fact = dailyCivicFact
            )

            Spacer(modifier = Modifier.height(24.dp))

            DailyCivicQuizCard(
                quizData = dailyCivicQuizData,
                onCardClick = {
                    println("📚 Daily Civic Quiz clicked - Opening quiz")
                    onNavigateToDailyCivicQuiz()
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
                }
            )
        }

        ChatbotFABContainer(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
            chatbotIconRes = chatbotIconRes,
            onFabClick = {
                println("🤖 Chatbot FAB clicked - Opening main chat")
                onNavigateToChat()
            },
            showPrompts = !hidePrompts,
            autoHidePromptsDuration = 4000L,
        )
    }
}