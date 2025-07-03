package com.newton.quiz.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.commonui.ui.SnackbarManager
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.quiz.presentation.view.quizgame.QuizGameScreen
import com.newton.quiz.presentation.view.quizinfo.QuizInfoScreen
import com.newton.quiz.presentation.view.results.QuizResultsScreen
import javax.inject.Inject

class QuizNavigationApiImpl @Inject constructor(
    private val snackbarManager: SnackbarManager
): QuizNavigationApi {

    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.QuizSubgraph.route,
            startDestination = NavigationRoutes.QuizInfoRoute.route
        ) {

            composable(
                route = NavigationRoutes.QuizInfoRoute.route
            ) {
                QuizInfoScreen(
                    onStartQuiz = { sessionId ->
                        navHostController.navigate(
                            NavigationRoutes.QuizGameRoute.createRoute(sessionId)
                        )
                    },
                    onViewLeaderboard = {
                    },
                    onNavigateBack = {
                        navHostController.popBackStack()
                    }
                )
            }

            composable(
                route = NavigationRoutes.QuizGameRoute.route
            ) { backStackEntry ->
                val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
                QuizGameScreen(
                    sessionId = sessionId,
                    onQuizComplete = { session ->
                        navHostController.navigate(
                            NavigationRoutes.QuizResultsRoute.createRoute(session)
                        ) {
                            popUpTo(NavigationRoutes.QuizInfoRoute.route) {
                                inclusive = false
                            }
                        }
                    },
                    onNavigateBack = {
                        navHostController.popBackStack()
                    }
                )
            }

            composable(
                route = NavigationRoutes.QuizResultsRoute.route
            ) { backStackEntry ->
                val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
                QuizResultsScreen(
                    sessionId = sessionId,
                    onViewLeaderboard = {

                    },
                    onRetakeQuiz = {
                        navHostController.navigate(NavigationRoutes.QuizInfoRoute.route) {
                            popUpTo(NavigationRoutes.QuizInfoRoute.route) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateBack = {
                        navHostController.popBackStack()
                    }
                )
            }


        }
    }
}