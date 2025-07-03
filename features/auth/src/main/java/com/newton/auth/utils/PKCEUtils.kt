package com.newton.auth.utils

import android.util.Base64
import com.newton.domain.models.oauth.PKCEData
import java.security.MessageDigest
import java.security.SecureRandom

object PKCEUtils {

    private const val CODE_VERIFIER_LENGTH = 128
    private const val STATE_LENGTH = 32

    /**
     * Generates PKCE data including code verifier, code challenge, and state
     */
    fun generatePKCEData(): PKCEData {
        val codeVerifier = generateCodeVerifier()
        val codeChallenge = generateCodeChallenge(codeVerifier)
        val state = generateState()

        return PKCEData(
            codeVerifier = codeVerifier,
            codeChallenge = codeChallenge,
            state = state
        )
    }

    /**
     * Generates a cryptographically secure code verifier
     */
    private fun generateCodeVerifier(): String {
        val bytes = ByteArray(CODE_VERIFIER_LENGTH)
        SecureRandom().nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.URL_SAFE or Base64.NO_PADDING)
    }

    /**
     * Generates code challenge from code verifier using SHA256
     */
    private fun generateCodeChallenge(codeVerifier: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(codeVerifier.toByteArray())
        return Base64.encodeToString(hash, Base64.URL_SAFE or Base64.NO_PADDING)
    }

    /**
     * Generates a cryptographically secure state parameter
     */
    private fun generateState(): String {
        val bytes = ByteArray(STATE_LENGTH)
        SecureRandom().nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.URL_SAFE or Base64.NO_PADDING)
    }

    /**
     * Validates state parameter to prevent CSRF attacks
     */
    fun validateState(receivedState: String, expectedState: String): Boolean {
        return receivedState == expectedState
    }
}