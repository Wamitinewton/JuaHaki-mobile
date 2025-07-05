package com.newton.quiz.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.newton.commonui.ui.SnackbarManager
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.quiz.presentation.quizgame.view.QuizGameScreen
import com.newton.quiz.presentation.quizinfo.view.QuizInfoScreen
import com.newton.quiz.presentation.quizresults.view.QuizResultsScreen
import javax.inject.Inject

class QuizNavigationApiImpl
    @Inject
    constructor(
        private val snackbarManager: SnackbarManager,
    ) : QuizNavigationApi {
        companion object {
            const val STATIC_SESSION_ID = "session_12345"
        }

        override fun registerNavigationGraph(
            navGraphBuilder: NavGraphBuilder,
            navHostController: NavHostController,
        ) {
            navGraphBuilder.navigation(
                route = NavigationSubgraphRoutes.QuizSubgraph.route,
                startDestination = NavigationRoutes.QuizInfoRoute.route,
            ) {
                composable(
                    route = NavigationRoutes.QuizInfoRoute.route,
                ) {
                    QuizInfoScreen(
                        onStartQuiz = { _ ->
                            navHostController.navigate(
                                NavigationRoutes.QuizGameRoute.createRoute(STATIC_SESSION_ID),
                            )
                        },
                        onViewLeaderboard = {
                        },
                        onNavigateBack = {
                            navHostController.popBackStack()
                        },
                    )
                }

                composable(
                    route = NavigationRoutes.QuizGameRoute.route,
                    arguments =
                        listOf(
                            navArgument("sessionId") {
                                type = NavType.StringType
                                defaultValue = STATIC_SESSION_ID
                            },
                        ),
                ) { backStackEntry ->
                    val sessionId = backStackEntry.arguments?.getString("sessionId") ?: STATIC_SESSION_ID
                    QuizGameScreen(
                        sessionId = sessionId,
                        onQuizComplete = { completedSessionId ->
                            navHostController.navigate(
                                NavigationRoutes.QuizResultsRoute.createRoute(completedSessionId),
                            ) {
                                popUpTo(NavigationRoutes.QuizInfoRoute.route) {
                                    inclusive = false
                                }
                            }
                        },
                        onNavigateBack = {
                            navHostController.popBackStack()
                        },
                    )
                }

                composable(
                    route = NavigationRoutes.QuizResultsRoute.route,
                    arguments =
                        listOf(
                            navArgument("sessionId") {
                                type = NavType.StringType
                                defaultValue = STATIC_SESSION_ID
                            },
                        ),
                ) { backStackEntry ->
                    val sessionId = backStackEntry.arguments?.getString("sessionId") ?: STATIC_SESSION_ID
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
                        },
                    )
                }
            }
        }
    }
