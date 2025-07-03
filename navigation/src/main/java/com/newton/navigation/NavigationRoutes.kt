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


    data object HomeScreenRoute: NavigationRoutes("home_screen_route")

    data object QuizInfoRoute: NavigationRoutes("quiz_info")

    data object QuizGameRoute: NavigationRoutes("quiz_game/{sessionId}") {

        fun createRoute(sessionId: String): String = "quiz_game/$sessionId"
    }

    data object QuizResultsRoute: NavigationRoutes("quiz_results") {
        fun createRoute(sessionId: String): String = "quiz_results/$sessionId"
    }

    data object QuizLeaderboardRoute: NavigationRoutes("quiz_leaderboard")

}
