package com.newton.juahaki.interceptor

import android.content.Context
import com.newton.database.sessionmanager.SessionManager
import com.newton.database.sessionmanager.TokenStoreKeys.KEY_ACCESS_TOKEN
import com.newton.database.sessionmanager.TokenStoreKeys.KEY_REFRESH_TOKEN
import com.newton.juahaki.interceptor.HeadersConstants.AUTHORIZATION_HEADER
import com.newton.juahaki.interceptor.HeadersConstants.BEARER_PREFIX
import com.newton.juahaki.interceptor.HeadersConstants.NEW_ACCESS_TOKEN_HEADER
import com.newton.juahaki.interceptor.HeadersConstants.REFRESH_HEADER
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor(
    context: Context,
) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sessionManager.fetchAccessToken()?.let {
            requestBuilder.addHeader(AUTHORIZATION_HEADER, "$BEARER_PREFIX $it")
        }

        sessionManager.fetchRefreshToken()?.let {
            requestBuilder.addHeader(REFRESH_HEADER, it)
        }

        val response = chain.proceed(requestBuilder.build())

        getTokensFromResHeaders(response)

        return response
    }

    private fun getTokensFromResHeaders(response: Response) {
        var accessToken = response.headers[KEY_ACCESS_TOKEN]

        val refreshToken = response.headers[KEY_REFRESH_TOKEN]

        val newAccessToken = response.headers[NEW_ACCESS_TOKEN_HEADER]

        if (newAccessToken != null) accessToken = newAccessToken

        if (accessToken != null && refreshToken != null) {
            sessionManager.saveAccessToken(accessToken)

            sessionManager.saveRefreshToken(refreshToken)

            Timber.tag(HeadersConstants.TAG).d("access: $accessToken")
        } else {
            Timber.tag(HeadersConstants.TAG).d("Not token ${response.headers}")
        }
    }
}
