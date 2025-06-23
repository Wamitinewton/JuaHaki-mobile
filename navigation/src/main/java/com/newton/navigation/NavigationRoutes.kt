package com.newton.navigation

sealed class NavigationRoutes(
    val route: String,
) {
    // Auth Module Routes
    data object SplashScreenRoute : NavigationRoutes("splash_screen_route")

    data object OnboardingRoute : NavigationRoutes("onboarding_screen_route")

    data object SignupRoute : NavigationRoutes("signup_screen_route")

    data object LoginRoute : NavigationRoutes("login_screen_route")

    data object AccountVerification : NavigationRoutes("account_verification_route/{email}") {
        const val EMAIL_ARG = "email"

        fun createRoute(email: String): String = "account_verification_route/$email"
    }

    data object ResetPasswordRoute: NavigationRoutes("reset_password/{email}") {
        const val EMAIL_ARG = "email"

        fun createRoute(email: String): String = "reset_password/$email"

    }

    data object ForgotPasswordRoute : NavigationRoutes("forgot_password_route")

}
