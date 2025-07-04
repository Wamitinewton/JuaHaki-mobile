package com.newton.auth.manager

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomTabsManager
    @Inject
    constructor() {
        fun launchOAuthFlow(
            context: Context,
            authorizationUrl: String,
        ): Boolean =
            try {
                val customTabsIntent =
                    CustomTabsIntent
                        .Builder()
                        .setShowTitle(true)
                        .setStartAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .build()

                customTabsIntent.launchUrl(context, authorizationUrl.toUri())
                true
            } catch (e: Exception) {
                launchSystemBrowser(context, authorizationUrl)
            }

        private fun launchSystemBrowser(
            context: Context,
            authorizationUrl: String,
        ): Boolean =
            try {
                val intent =
                    Intent(Intent.ACTION_VIEW, authorizationUrl.toUri()).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                context.startActivity(intent)
                true
            } catch (e: Exception) {
                false
            }
    }
