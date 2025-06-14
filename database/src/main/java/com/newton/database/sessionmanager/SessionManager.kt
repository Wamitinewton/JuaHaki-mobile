package com.newton.database.sessionmanager

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.newton.database.sessionmanager.TokenStoreKeys.KEY_ACCESS_TOKEN
import com.newton.database.sessionmanager.TokenStoreKeys.KEY_REFRESH_TOKEN
import com.newton.database.sessionmanager.TokenStoreKeys.PREF_FILE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        private val masterKey by lazy {
            MasterKey
                .Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
        }

        private val securePreferences by lazy {
            try {
                EncryptedSharedPreferences.create(
                    context,
                    PREF_FILE_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
                )
            } catch (e: Exception) {
                context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE).edit {
                    clear()
                }
                EncryptedSharedPreferences.create(
                    context,
                    PREF_FILE_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
                )
            }
        }

        fun saveAccessToken(token: String) {
            securePreferences.edit().apply {
                putString(KEY_ACCESS_TOKEN, token)
                apply()
            }
        }

        fun fetchAccessToken(): String? = securePreferences.getString(KEY_ACCESS_TOKEN, null)

        fun saveRefreshToken(token: String) {
            securePreferences.edit().apply {
                putString(KEY_REFRESH_TOKEN, token)
                apply()
            }
        }

        fun fetchRefreshToken(): String? = securePreferences.getString(KEY_REFRESH_TOKEN, null)

        fun saveTokens(
            accessToken: String,
            refreshToken: String,
        ) {
            securePreferences.edit().apply {
                putString(KEY_ACCESS_TOKEN, accessToken)
                putString(KEY_REFRESH_TOKEN, refreshToken)
                apply()
            }
        }

        fun updateTokens(
            accessToken: String?,
            refreshToken: String?,
        ) {
            securePreferences.edit().apply {
                accessToken?.let { putString(KEY_ACCESS_TOKEN, it) }
                refreshToken?.let { putString(KEY_REFRESH_TOKEN, it) }
                apply()
            }
        }

        fun clearTokens() {
            securePreferences.edit { clear() }
        }
    }
