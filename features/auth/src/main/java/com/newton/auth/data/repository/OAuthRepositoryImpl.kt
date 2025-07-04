package com.newton.auth.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.newton.auth.utils.PKCEUtils
import com.newton.core.enums.OAuthProvider
import com.newton.core.utils.Resource
import com.newton.data.mappers.toJwtData
import com.newton.data.remote.auth.OAuthApiService
import com.newton.data.remote.safeApiCall
import com.newton.database.dao.UserDao
import com.newton.database.mappers.toUserEntity
import com.newton.database.sessionmanager.SessionManager
import com.newton.domain.models.auth.JwtData
import com.newton.domain.models.oauth.OAuthAuthorizationData
import com.newton.domain.models.oauth.OAuthTokenRequest
import com.newton.domain.models.oauth.PKCEData
import com.newton.domain.repository.oauth.OAuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OAuthRepositoryImpl
    @Inject
    constructor(
        private val oAuthApiService: OAuthApiService,
        private val sessionManager: SessionManager,
        private val userDao: UserDao,
        @ApplicationContext private val context: Context,
    ) : OAuthRepository {
        companion object {
            private const val OAUTH_PREFS = "oauth_prefs"
            private const val KEY_CODE_VERIFIER = "code_verifier"
            private const val KEY_CODE_CHALLENGE = "code_challenge"
            private const val KEY_STATE = "state"
        }

        private val oauthPrefs: SharedPreferences by lazy {
            context.getSharedPreferences(OAUTH_PREFS, Context.MODE_PRIVATE)
        }

        override fun generatePKCEData(): PKCEData = PKCEUtils.generatePKCEData()

        override suspend fun getAuthorizationUrl(
            provider: OAuthProvider,
            codeVerifier: String,
            state: String,
        ): Flow<Resource<OAuthAuthorizationData>> =
            safeApiCall(
                apiCall = {
                    val response =
                        oAuthApiService.getAuthorizationUrl(
                            provider = provider.providerName,
                            codeVerifier = codeVerifier,
                            state = state,
                        )
                    response.data!!
                },
                errorHandler = { throwable ->
                    "Failed to get authorization URL: ${throwable.localizedMessage}"
                },
            ).map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val data = resource.data!!
                        Resource.Success(
                            OAuthAuthorizationData(
                                authorizationUrl = data.authorizationUrl,
                                state = data.state,
                                codeChallenge = data.codeChallenge,
                                codeVerifier = codeVerifier,
                            ),
                        )
                    }
                    is Resource.Error ->
                        Resource.Error(
                            message = resource.message ?: "Unknown error",
                            errorType = resource.errorType ?: com.newton.core.enums.ErrorType.UNKNOWN,
                            httpCode = resource.httpCode,
                        )
                    is Resource.Loading -> Resource.Loading(resource.isLoading)
                }
            }

        override suspend fun exchangeCodeForTokens(
            provider: OAuthProvider,
            tokenRequest: OAuthTokenRequest,
        ): Flow<Resource<JwtData>> =
            safeApiCall(
                apiCall = {
                    val response =
                        oAuthApiService.exchangeCodeForTokens(
                            provider = provider.providerName,
                            code = tokenRequest.code,
                            state = tokenRequest.state,
                        )

                    val jwtData = response.data!!.toJwtData()

                    // Store tokens and user data
                    sessionManager.saveTokens(jwtData.accessToken, jwtData.refreshToken)
                    userDao.insertUser(jwtData.userInfo.toUserEntity())

                    jwtData
                },
                errorHandler = { throwable ->
                    "Failed to exchange code for tokens: ${throwable.localizedMessage}"
                },
            )

        override suspend fun storePKCEData(pkceData: PKCEData) {
            oauthPrefs.edit().apply {
                putString(KEY_CODE_VERIFIER, pkceData.codeVerifier)
                putString(KEY_CODE_CHALLENGE, pkceData.codeChallenge)
                putString(KEY_STATE, pkceData.state)
                apply()
            }
        }

        override suspend fun getPKCEData(): PKCEData? {
            val codeVerifier = oauthPrefs.getString(KEY_CODE_VERIFIER, null)
            val codeChallenge = oauthPrefs.getString(KEY_CODE_CHALLENGE, null)
            val state = oauthPrefs.getString(KEY_STATE, null)

            return if (codeVerifier != null && codeChallenge != null && state != null) {
                PKCEData(codeVerifier, codeChallenge, state)
            } else {
                null
            }
        }

        override suspend fun clearPKCEData() {
            oauthPrefs.edit { clear() }
        }
    }
