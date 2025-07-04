package com.newton.auth.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.newton.auth.presentation.accountverification.view.AccountVerificationContainer
import com.newton.auth.presentation.accountverification.viewmodel.AccountVerificationViewModel
import com.newton.auth.presentation.forgotpassword.view.ForgotPasswordContainer
import com.newton.auth.presentation.forgotpassword.view.ResetPasswordContainer
import com.newton.auth.presentation.forgotpassword.viewmodel.InitiatePasswordResetViewModel
import com.newton.auth.presentation.forgotpassword.viewmodel.ResetPasswordViewModel
import com.newton.auth.presentation.login.view.LoginContainer
import com.newton.auth.presentation.login.viewmodel.LoginViewModel
import com.newton.auth.presentation.oauth.viewmodel.OAuthViewModel
import com.newton.auth.presentation.onboarding.OnboardingScreen
import com.newton.auth.presentation.signup.view.SignUpContainer
import com.newton.auth.presentation.signup.viewmodel.SignupViewModel
import com.newton.auth.presentation.splash.SplashScreen
import com.newton.auth.presentation.splash.SplashViewModel
import com.newton.commonui.ui.SnackbarManager
import com.newton.core.enums.TransitionType
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.navigation.NavigationTransitions
import javax.inject.Inject

class AuthNavigationApiImpl
    @Inject
    constructor(
        private val snackbarManager: SnackbarManager,
    ) : AuthNavigationApi {
        private val navigationTransitions = NavigationTransitions()

        override fun registerNavigationGraph(
            navGraphBuilder: NavGraphBuilder,
            navHostController: NavHostController,
        ) {
            navGraphBuilder.navigation(
                route = NavigationSubgraphRoutes.AuthSubgraph.route,
                startDestination = NavigationRoutes.SplashScreenRoute.route,
            ) {
                composable(
                    route = NavigationRoutes.SplashScreenRoute.route,
                    enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                    exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                    popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                    popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
                ) {
                    val splashViewModel = hiltViewModel<SplashViewModel>()
                    SplashScreen(
                        onSplashComplete = { hasTokens ->
                            if (hasTokens) {
                                navHostController.navigate(NavigationRoutes.HomeScreenRoute.route) {
                                    popUpTo(NavigationRoutes.SplashScreenRoute.route) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                navHostController.navigate(NavigationRoutes.OnboardingRoute.route) {
                                    popUpTo(NavigationRoutes.SplashScreenRoute.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        viewModel = splashViewModel,
                    )
                }

                composable(
                    route = NavigationRoutes.OnboardingRoute.route,
                    enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                    exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                    popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                    popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
                ) {
                    OnboardingScreen(
                        onOnboardingComplete = {
                            navHostController.navigate(NavigationRoutes.SignupRoute.route) {
                                popUpTo(NavigationRoutes.OnboardingRoute.route) {
                                    inclusive = true
                                }
                            }
                        },
                    )
                }

                composable(
                    route = NavigationRoutes.SignupRoute.route,
                    enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                    exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                    popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                    popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
                ) {
                    val signupViewModel = hiltViewModel<SignupViewModel>()
                    SignUpContainer(
                        onNavigateBack = {
                            navHostController.popBackStack()
                        },
                        onNavigateToLogin = {
                            navHostController.navigate(NavigationRoutes.LoginRoute.route) {
                                popUpTo(NavigationRoutes.SignupRoute.route)
                            }
                        },
                        onNavigateToEmailVerification = { email ->
                            navHostController.navigate(NavigationRoutes.AccountVerification.createRoute(email)) {
                                popUpTo(NavigationRoutes.SignupRoute.route)
                            }
                        },
                        onSignUpWithGoogle = {
                        },
                        onPrivacyPolicyClick = {
                        },
                        onTermsOfServiceClick = {
                        },
                        onShowSnackbar = { snackbarData ->
                            snackbarManager.showSnackbar(snackbarData)
                        },
                        viewModel = signupViewModel,
                    )
                }

                composable(
                    route = NavigationRoutes.LoginRoute.route,
                    enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                    exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                    popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                    popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
                ) {
                    val loginViewModel = hiltViewModel<LoginViewModel>()
                    val oAuthViewModel = hiltViewModel<OAuthViewModel>()

                    LoginContainer(
                        onNavigateBack = {
                            navHostController.popBackStack()
                        },
                        onNavigateToSignUp = {
                            navHostController.navigate(NavigationRoutes.SignupRoute.route) {
                                popUpTo(NavigationRoutes.LoginRoute.route)
                            }
                        },
                        onNavigateToHome = {
                            navHostController.navigate(NavigationRoutes.HomeScreenRoute.route) {
                                popUpTo(NavigationRoutes.LoginRoute.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateToForgotPassword = {
                            navHostController.navigate(NavigationRoutes.ForgotPasswordRoute.route) {
                                popUpTo(NavigationRoutes.LoginRoute.route)
                            }
                        },
                        onNavigateToActivateAccount = { email ->
                            navHostController.navigate(NavigationRoutes.AccountVerification.createRoute(email))
                        },
                        onShowSnackbar = { snackbarData ->
                            snackbarManager.showSnackbar(snackbarData)
                        },
                        onShowToast = {},
                        loginViewModel = loginViewModel,
                        oAuthViewModel = oAuthViewModel,
                    )
                }

                composable(
                    route = NavigationRoutes.AccountVerification.route,
                    enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                    exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                    popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                    popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
                    arguments =
                        listOf(
                            navArgument(NavigationRoutes.AccountVerification.EMAIL_ARG) {
                                type = NavType.StringType
                            },
                        ),
                ) { backstackEntry ->

                    val accountVerificationViewModel = hiltViewModel<AccountVerificationViewModel>()
                    AccountVerificationContainer(
                        onNavigateBack = {
                            navHostController.popBackStack()
                        },
                        onNavigateToLogin = {
                            navHostController.navigate(NavigationRoutes.LoginRoute.route) {
                                popUpTo(NavigationRoutes.AccountVerification.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onShowSnackbar = { snackbarData ->
                            snackbarManager.showSnackbar(snackbarData)
                        },
                        viewModel = accountVerificationViewModel,
                    )
                }

                composable(
                    route = NavigationRoutes.ForgotPasswordRoute.route,
                    enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                    exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                    popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                    popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
                ) {
                    val initiatePasswordResetViewModel = hiltViewModel<InitiatePasswordResetViewModel>()
                    ForgotPasswordContainer(
                        onNavigateBack = {
                            navHostController.popBackStack()
                        },
                        onNavigateToLogin = {
                            navHostController.navigate(NavigationRoutes.LoginRoute.route) {
                                popUpTo(NavigationRoutes.ForgotPasswordRoute.route)
                            }
                        },
                        onNavigateToResetPassword = { email ->
                            navHostController.navigate(NavigationRoutes.ResetPasswordRoute.createRoute(email)) {
                                popUpTo(NavigationRoutes.ForgotPasswordRoute.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onShowSnackbar = { snackbarData ->
                            snackbarManager.showSnackbar(snackbarData)
                        },
                        onShowToast = {},
                        viewModel = initiatePasswordResetViewModel,
                    )
                }

                composable(
                    route = NavigationRoutes.ResetPasswordRoute.route,
                    enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                    exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                    popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                    popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
                    arguments =
                        listOf(
                            navArgument(NavigationRoutes.ResetPasswordRoute.EMAIL_ARG) {
                                type = NavType.StringType
                            },
                        ),
                ) { backstackEntry ->
                    val resetPasswordViewModel = hiltViewModel<ResetPasswordViewModel>()
                    ResetPasswordContainer(
                        onNavigateBack = {
                            navHostController.popBackStack()
                        },
                        onNavigateToLogin = {
                            navHostController.navigate(NavigationRoutes.LoginRoute.route) {
                                popUpTo(NavigationRoutes.ResetPasswordRoute.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateToInitiatePasswordReset = {
                            navHostController.navigateUp()
                        },
                        onShowSnackbar = { snackbarData ->
                            snackbarManager.showSnackbar(snackbarData)
                        },
                        onShowToast = {},
                        viewModel = resetPasswordViewModel,
                    )
                }
            }
        }
    }
