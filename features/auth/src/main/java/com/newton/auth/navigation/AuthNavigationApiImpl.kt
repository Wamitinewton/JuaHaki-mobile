package com.newton.auth.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.newton.auth.presentation.forgotpassword.view.ForgotPasswordScreen
import com.newton.auth.presentation.login.view.LoginContainer
import com.newton.auth.presentation.login.viewmodel.LoginViewModel
import com.newton.auth.presentation.onboarding.OnboardingScreen
import com.newton.auth.presentation.otp.view.AccountVerificationScreen
import com.newton.auth.presentation.otp.view.OtpVerificationScreen
import com.newton.auth.presentation.otp.viewmodel.AccountVerificationViewModel
import com.newton.auth.presentation.resetpassword.view.ResetPasswordScreen
import com.newton.auth.presentation.signup.view.SignUpContainer
import com.newton.auth.presentation.signup.viewmodel.SignupViewModel
import com.newton.auth.presentation.splash.SplashScreen
import com.newton.commonui.ui.SnackbarManager
import com.newton.core.enums.OtpContext
import com.newton.core.enums.TransitionType
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.navigation.NavigationTransitions
import javax.inject.Inject

class AuthNavigationApiImpl @Inject constructor(
    private val snackbarManager: SnackbarManager
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
                SplashScreen(
                    onSplashComplete = {
                        navHostController.navigate(NavigationRoutes.OnboardingRoute.route) {
                            popUpTo(NavigationRoutes.SplashScreenRoute.route) {
                                inclusive = true
                            }
                        }
                    },
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
                        navHostController.navigate(NavigationRoutes.OTPRoute.createRoute(email)) {
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

                    viewModel = signupViewModel
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
                        navHostController.navigate(NavigationRoutes.OTPRoute.route) {
                            popUpTo(NavigationRoutes.OTPRoute.route)
                        }
                    },
                    onNavigateToForgotPassword = {
                        navHostController.navigate(NavigationRoutes.ForgotPasswordRoute.route) {
                            popUpTo(NavigationRoutes.LoginRoute.route)
                        }
                    },
                    onLoginWithGoogle = {},
                    onPrivacyPolicyClick = {},
                    onTermsOfServiceClick = {},
                    onShowSnackbar = { snackbarData ->
                        snackbarManager.showSnackbar(snackbarData)
                    },
                    onShowToast = {},
                    viewModel = loginViewModel,
                    onNavigateToActivateAccount = { email ->
                        navHostController.navigate(NavigationRoutes.OTPRoute.createRoute(email))
                    },
                )
            }

            composable(
                route = NavigationRoutes.OTPRoute.route,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
                arguments = listOf(
                    navArgument(NavigationRoutes.OTPRoute.EMAIL_ARG) {
                        type = NavType.StringType
                    }
                ),
            ) { backstackEntry ->

                val accountVerificationViewModel = hiltViewModel<AccountVerificationViewModel>()
                AccountVerificationScreen(
                    onNavigateBack = {
                        navHostController.popBackStack()
                    },
                    onNavigateToLogin = {
                        navHostController.navigate(NavigationRoutes.LoginRoute.route) {
                            popUpTo(NavigationRoutes.OTPRoute.route) {
                                inclusive = true
                            }
                        }
                    },
                    onShowSnackbar = { snackbarData ->
                        snackbarManager.showSnackbar(snackbarData)
                    },
                    viewModel = accountVerificationViewModel
                )

            }

            composable(
                route = NavigationRoutes.ForgotPasswordRoute.route,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
            ) {
                ForgotPasswordScreen(
                    onNavigateBack = {},
                    onNavigateToLogin = {},
                    onSendResetEmail = {
                        navHostController.navigate(NavigationRoutes.OTPRoute.createRoute("wamitinewton@gmail.com")) {
                            popUpTo(NavigationRoutes.ForgotPasswordRoute.route)
                        }
                    },
                    isLoading = false,
                )
            }

            composable(
                route = NavigationRoutes.ResetPassword.route,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
            ) {
                ResetPasswordScreen(
                    onNavigateBack = {},
                    onNavigateToLogin = {},
                    onResetPassword = {
                        navHostController.navigate(NavigationRoutes.LoginRoute.route) {
                            popUpTo(NavigationRoutes.ResetPassword.route) {
                                inclusive = true
                            }
                        }
                    },
                    isLoading = false,
                    email = "example@gmail.com",
                )
            }
        }
    }
}
