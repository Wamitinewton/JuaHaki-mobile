package com.newton.auth.data

import com.newton.database.sessionmanager.SessionManager

object AuthTokenHolder {
    var accessToken: String? = null
    var refreshToken: String? = null

    fun initializeTokens(sessionManager: SessionManager) {
        accessToken = sessionManager.fetchAccessToken()
        refreshToken = sessionManager.fetchRefreshToken()
    }
}