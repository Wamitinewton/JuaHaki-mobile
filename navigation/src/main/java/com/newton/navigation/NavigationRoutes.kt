package com.newton.navigation

import com.newton.core.enums.OtpContext

sealed class NavigationRoutes(
    val route: String,
) {
    // Auth Module Routes
    data object SplashScreenRoute : NavigationRoutes("splash_screen_route")

    data object OnboardingRoute : NavigationRoutes("onboarding_screen_route")

    data object SignupRoute : NavigationRoutes("signup_screen_route")

    data object LoginRoute : NavigationRoutes("login_screen_route")

    object OTPRoute : NavigationRoutes("otp/{email}") {
        const val EMAIL_ARG = "email"

        fun createRoute(email: String): String = "otp/$email"
    }


    data object ForgotPasswordRoute : NavigationRoutes("forgot_password_route")

    data object ResetPassword : NavigationRoutes("reset_password_route")
}
