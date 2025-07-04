package com.newton.domain.repository.oauth

import com.newton.core.enums.OAuthProvider
import com.newton.core.utils.Resource
import com.newton.domain.models.auth.JwtData
import com.newton.domain.models.oauth.OAuthAuthorizationData
import com.newton.domain.models.oauth.OAuthTokenRequest
import com.newton.domain.models.oauth.PKCEData
import kotlinx.coroutines.flow.Flow

interface OAuthRepository {
    /**
     * Generate PKCE data for secure OAuth flow
     */
    fun generatePKCEData(): PKCEData

    /**
     * Get authorization URL for OAuth provider
     */
    suspend fun getAuthorizationUrl(
        provider: OAuthProvider,
        codeVerifier: String,
        state: String,
    ): Flow<Resource<OAuthAuthorizationData>>

    /**
     * Exchange authorization code for tokens
     */
    suspend fun exchangeCodeForTokens(
        provider: OAuthProvider,
        tokenRequest: OAuthTokenRequest,
    ): Flow<Resource<JwtData>>

    /**
     * Store OAuth session data temporarily
     */
    suspend fun storePKCEData(pkceData: PKCEData)

    /**
     * Retrieve stored PKCE data
     */
    suspend fun getPKCEData(): PKCEData?

    /**
     * Clear stored PKCE data
     */
    suspend fun clearPKCEData()
}
