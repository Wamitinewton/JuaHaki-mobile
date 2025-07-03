package com.newton.juahaki.application

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.newton.auth.presentation.oauth.viewmodel.OAuthViewModel
import com.newton.core.enums.OAuthProvider
import com.newton.domain.models.oauth.OAuthTokenRequest
import com.newton.navigation.NavigationRoutes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class OAuthCallbackActivity : ComponentActivity() {

    private val oAuthViewModel: OAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleOAuthCallback(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleOAuthCallback(intent)
    }

    private fun handleOAuthCallback(intent: Intent) {
        val uri = intent.data

        if (uri == null) {
            Timber.e("No URI data received in OAuth callback")
            navigateToMainActivity(false, "No callback data received")
            return
        }

        Timber.d("OAuth callback received: $uri")

        val code = uri.getQueryParameter("code")
        val state = uri.getQueryParameter("state")
        val error = uri.getQueryParameter("error")
        val errorDescription = uri.getQueryParameter("error_description")

        when {
            error != null -> {
                Timber.e("OAuth error: $error - $errorDescription")
                navigateToMainActivity(false, errorDescription ?: error)
            }

            code != null && state != null -> {
                Timber.d("OAuth success - processing code exchange")
                processAuthorizationCode(code, state)
            }

            else -> {
                Timber.e("Invalid OAuth callback - missing required parameters")
                navigateToMainActivity(false, "Invalid callback parameters")
            }
        }
    }

    private fun processAuthorizationCode(code: String, state: String) {
        lifecycleScope.launch {
            try {
                val pkceData = oAuthViewModel.getPKCEData()

                if (pkceData == null) {
                    Timber.e("No PKCE data found")
                    navigateToMainActivity(false, "OAuth session expired")
                    return@launch
                }

                if (pkceData.state != state) {
                    Timber.e("State mismatch in OAuth callback")
                    navigateToMainActivity(false, "Security error: Invalid state")
                    return@launch
                }

                val tokenRequest = OAuthTokenRequest(
                    code = code,
                    codeVerifier = pkceData.codeVerifier,
                    state = state
                )

                oAuthViewModel.exchangeCodeForTokens(OAuthProvider.GOOGLE, tokenRequest) { success, message ->
                    navigateToMainActivity(success, message)
                }

            } catch (e: Exception) {
                Timber.e(e, "Error processing OAuth callback")
                navigateToMainActivity(false, "Authentication failed")
            }
        }
    }

    private fun navigateToMainActivity(success: Boolean, message: String? = null) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("oauth_success", success)
            putExtra("oauth_message", message)

            if (success) {
                putExtra("navigate_to", NavigationRoutes.HomeScreenRoute.route)
            }
        }

        startActivity(intent)
        finish()
    }
}