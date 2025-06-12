package com.newton.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.auth.presentation.forgotpassword.view.ForgotPasswordScreen
import com.newton.auth.presentation.login.view.LoginScreen
import com.newton.auth.presentation.onboarding.OnboardingScreen
import com.newton.auth.presentation.otp.view.OtpVerificationScreen
import com.newton.auth.presentation.signup.view.SignUpScreen
import com.newton.auth.presentation.splash.SplashScreen
import com.newton.core.enums.TransitionType
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.navigation.NavigationTransitions

class AuthNavigationApiImpl: AuthNavigationApi {

    private val navigationTransitions = NavigationTransitions()

    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.AuthSubgraph.route,
            startDestination = NavigationRoutes.SplashScreenRoute.route
        ) {
            composable(
                route = NavigationRoutes.SplashScreenRoute.route,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300)
            ) {
                SplashScreen(
                    onSplashComplete = {
                        navHostController.navigate(NavigationRoutes.OnboardingRoute.route) {
                            popUpTo(NavigationRoutes.SplashScreenRoute.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(
                route = NavigationRoutes.OnboardingRoute.route,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300)
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
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300)
            ) {
                SignUpScreen(
                    onNavigateBack = {},
                    onNavigateToLogin = {
                        navHostController.navigate(NavigationRoutes.LoginRoute.route) {
                            popUpTo(NavigationRoutes.SignupRoute.route)
                        }
                    },
                    onSignUpWithEmail = {
                        navHostController.navigate(NavigationRoutes.OTPRoute.route) {
                            popUpTo(NavigationRoutes.SignupRoute.route)
                        }
                    },
                    onSignUpWithGoogle = {},
                    onPrivacyPolicyClick = {},
                    onTermsOfServiceClick = {},
                    isLoading = false
                )
            }

            composable(
                route = NavigationRoutes.LoginRoute.route,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300)
            ) {
                LoginScreen(
                    onNavigateBack = {},
                    onPrivacyPolicyClick = {},
                    onTermsOfServiceClick = {},
                    isLoading = false,
                    onNavigateToSignUp = {
                        navHostController.navigate(NavigationRoutes.SignupRoute.route) {
                            popUpTo(NavigationRoutes.LoginRoute.route)
                        }
                    },
                    onLoginWithEmail = {},
                    onLoginWithGoogle = {},
                    onForgotPasswordClick = {
                        navHostController.navigate(NavigationRoutes.ForgotPasswordRoute.route) {
                            popUpTo(NavigationRoutes.LoginRoute.route)
                        }
                    },
                )
            }

            composable(
                route = NavigationRoutes.OTPRoute.route,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300)
            ) {
                OtpVerificationScreen(
                    onNavigateBack = { /* navigation */ },
                    onVerifyOtp = { otp -> /* verify OTP */ },
                    onResendCode = { /* resend logic */ },
                    isLoading = false,
                    contactInfo = "user@example.com",
                    otpLength = 6,
                    resendCooldownSeconds = 60
                )
            }

            composable(
                route = NavigationRoutes.ForgotPasswordRoute.route,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300)
            ) {
                ForgotPasswordScreen(
                    onNavigateBack = {},
                    onNavigateToLogin = {},
                    onSendResetEmail = {
                        navHostController.navigate(NavigationRoutes.OTPRoute.route) {
                            popUpTo(NavigationRoutes.ForgotPasswordRoute.route)
                        }
                    },
                    isLoading = false
                )
            }
        }
    }
}