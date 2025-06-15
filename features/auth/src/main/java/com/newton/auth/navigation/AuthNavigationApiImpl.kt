package com.newton.auth.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.newton.auth.presentation.forgotpassword.view.ForgotPasswordScreen
import com.newton.auth.presentation.login.view.LoginScreen
import com.newton.auth.presentation.onboarding.OnboardingScreen
import com.newton.auth.presentation.otp.view.OtpVerificationScreen
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
                    onNavigateToEmailVerification = {
                        navHostController.navigate(NavigationRoutes.OTPRoute.createRoute(OtpContext.SIGN_UP)) {
                            popUpTo(NavigationRoutes.SignupRoute.route)
                        }
                    },
                    onSignUpWithGoogle = {
                    },
                    onPrivacyPolicyClick = {
                    },
                    onTermsOfServiceClick = {
                    },
                    onShowSnackbar = { message ->
                        snackbarManager.showError(message)
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
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
                arguments =
                    listOf(
                        navArgument(NavigationRoutes.OTPRoute.CONTEXT_ARG) {
                            type = NavType.StringType
                        },
                    ),
            ) { backstackEntry ->
                val contextString = backstackEntry.arguments?.getString(NavigationRoutes.OTPRoute.CONTEXT_ARG)
                val context = OtpContext.valueOf(contextString ?: OtpContext.SIGN_UP.name)

                OtpVerificationScreen(
                    onNavigateBack = {
                        navHostController.popBackStack()
                    },
                    onVerifyOtp = { otpCode ->
                        when (context) {
                            OtpContext.SIGN_UP -> {
                                navHostController.navigate(NavigationRoutes.LoginRoute.route) {
                                    popUpTo(NavigationRoutes.OTPRoute.route) {
                                        inclusive = true
                                    }
                                }
                            }
                            OtpContext.FORGOT_PASSWORD -> {
                                navHostController.navigate(NavigationRoutes.ResetPassword.route) {
                                    popUpTo(NavigationRoutes.OTPRoute.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    },
                    onResendCode = { /* resend logic */ },
                    isLoading = false,
                    contactInfo = "user@example.com",
                    otpLength = 6,
                    resendCooldownSeconds = 60,
                    title =
                        when (context) {
                            OtpContext.SIGN_UP -> "Complete Your Registration"
                            OtpContext.FORGOT_PASSWORD -> "Verify Your Identity"
                        },
                    subtitle =
                        when (context) {
                            OtpContext.SIGN_UP -> "We've sent you a verification code"
                            OtpContext.FORGOT_PASSWORD -> "We've sent you a password reset code"
                        },
                    description =
                        when (context) {
                            OtpContext.SIGN_UP -> "Enter the code we sent to complete your account setup"
                            OtpContext.FORGOT_PASSWORD -> "Enter the code we sent to reset your password"
                        },
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
                        navHostController.navigate(NavigationRoutes.OTPRoute.createRoute(OtpContext.FORGOT_PASSWORD)) {
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
