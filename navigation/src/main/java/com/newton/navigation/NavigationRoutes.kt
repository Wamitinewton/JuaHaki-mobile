package com.newton.navigation

sealed class NavigationRoutes(val route: String) {
    data object SplashScreenRoute: NavigationRoutes("splash_screen_route")
    data object OnboardingRoute: NavigationRoutes("onboarding_screen_route")
    data object SignupRoute: NavigationRoutes("signup_screen_route")
    data object LoginRoute: NavigationRoutes("login_screen_route")
    data object OTPRoute: NavigationRoutes("otp_screen_input")
}