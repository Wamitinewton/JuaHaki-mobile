package com.newton.quiz.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.newton.commonui.ui.SnackbarManager
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.quiz.presentation.quizgame.view.QuizGameContainer
import com.newton.quiz.presentation.quizinfo.view.QuizInfoContainer
import com.newton.quiz.presentation.quizinfo.viewmodel.QuizInfoViewModel
import com.newton.quiz.presentation.quizresults.view.QuizResultsContainer
import javax.inject.Inject

class QuizNavigationApiImpl
@Inject
constructor(
    private val snackbarManager: SnackbarManager,
) : QuizNavigationApi {

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
                val viewModel = hiltViewModel<QuizInfoViewModel>()
                QuizInfoContainer(
                    onStartQuiz = { sessionId ->
                        if (sessionId.isNotEmpty()) {
                            navHostController.navigate(
                                NavigationRoutes.QuizGameRoute.createRoute(sessionId)
                            )
                        }
                    },
                    onViewLeaderboard = {
                    },
                    onNavigateBack = {
                        navHostController.popBackStack()
                    },
                    onShowSnackbar = { snackbarData ->
                        snackbarManager.showSnackbar(snackbarData)
                    },
                    onShowToast = {
                    },
                    viewModel = viewModel,
                )
            }

            composable(
                route = NavigationRoutes.QuizGameRoute.route,
                arguments =
                    listOf(
                        navArgument("sessionId") {
                            type = NavType.StringType
                        },
                    ),
            ) { backStackEntry ->
                val sessionId = backStackEntry.arguments?.getString("sessionId")

                if (sessionId.isNullOrEmpty()) {
                    navHostController.popBackStack()
                    return@composable
                }

                QuizGameContainer(
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
                    onShowSnackbar = { snackbarData ->
                        snackbarManager.showSnackbar(snackbarData)
                    },
                    onShowToast = {
                    },
                )
            }

            composable(
                route = NavigationRoutes.QuizResultsRoute.route,
                arguments =
                    listOf(
                        navArgument("sessionId") {
                            type = NavType.StringType
                        },
                    ),
            ) { backStackEntry ->
                val sessionId = backStackEntry.arguments?.getString("sessionId")

                if (sessionId.isNullOrEmpty()) {
                    navHostController.popBackStack()
                    return@composable
                }

                QuizResultsContainer(
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
                    onShowSnackbar = { snackbarData ->
                        snackbarManager.showSnackbar(snackbarData)
                    },
                    onShowToast = {
                    },
                )
            }
        }
    }
}