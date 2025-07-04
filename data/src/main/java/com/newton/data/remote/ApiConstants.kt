package com.newton.data.remote

object ApiConstants {
    const val SIGN_UP = "/api/v1/auth/signup"
    const val LOGIN = "/api/v1/auth/login"
    const val REFRESH_TOKEN = "/api/v1/auth/refresh"
    const val VERIFY_EMAIL = "/api/v1/auth/verify-email"
    const val RESEND_EMAIL_VERIFICATION = "/api/v1/auth/resend-verification"

    // OAUTH
    const val GET_AUTH_URL = "/api/v1/auth/oauth2/authorize-url"
    const val EXCHANGE_CODE_FOR_TOKENS = "/api/v1/auth/oauth2/callback/{provider}"

    // User Management
    const val INITIATE_RESET_PASSWORD = "/api/v1/user/password/reset/initiate"
    const val RESET_PASSWORD = "/api/v1/user/password/reset/confirm"

    // QUIZ
    const val GET_TODAY_QUIZ = "/api/v1/users/quiz/civic/today"
    const val GET_QUIZ_INFO = "/api/v1/users/quiz/civic/info"
    const val START_QUIZ = "/api/v1/users/quiz/civic/start"
    const val SUBMIT_ANSWER = "/api/v1/users/quiz/civic/submit-answer"
    const val GET_SESSION_STATUS = "/api/v1/users/quiz/civic/session/{sessionId}/status"
    const val ABANDON_SESSION = "/api/v1/users/quiz/civic/session/{sessionId}/abandon"
    const val GET_QUIZ_RESULTS = "/api/v1/users/quiz/civic/results/{sessionId}"
    const val GET_QUIZ_HISTORY_METADATA = "/api/v1/users/quiz/civic/history/metadata"
    const val GET_QUIZ_HISTORY_DETAILS = "/api/v1/users/quiz/civic/history/details/{sessionId}"
    const val GET_TODAY_LEADERBOARD = "/api/v1/users/quiz/civic/leaderboard/today"
    const val GET_LEADERBOARD = "/api/v1/users/quiz/civic/leaderboard"
    const val GET_QUIZ_STATISTICS = "/api/v1/users/quiz/civic/statistics"
}
