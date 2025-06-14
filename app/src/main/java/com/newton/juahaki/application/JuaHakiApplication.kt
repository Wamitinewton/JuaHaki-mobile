package com.newton.juahaki.application

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkerFactory
import com.newton.auth.manager.TokenRefreshManager
import com.newton.domain.repository.auth.AuthRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class JuaHakiApplication :
    Application(),
    Configuration.Provider {
    @Inject
    lateinit var workerFactory: WorkerFactory

    @Inject
    lateinit var tokenRefreshManager: TokenRefreshManager

    @Inject
    lateinit var authRepository: AuthRepository

    // Application-scoped coroutine scope
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()



        // Initialize token refresh in background
        initializeTokenRefresh()
    }

    private fun initializeTokenRefresh() {
        applicationScope.launch {
            try {
                // Only start token refresh if user has a refresh token
                val refreshToken = authRepository.getRefreshToken()
                if (refreshToken != null) {
                    tokenRefreshManager.startPeriodicTokenRefresh()
                    Timber.d("Token refresh initialized for authenticated user")
                } else {
                    Timber.d("No refresh token found, skipping token refresh initialization")
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to initialize token refresh")
            }
        }
    }

    override val workManagerConfiguration: Configuration
        get() =
            Configuration
                .Builder()
                .setWorkerFactory(workerFactory)
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .setMaxSchedulerLimit(4)
                .build()
}
