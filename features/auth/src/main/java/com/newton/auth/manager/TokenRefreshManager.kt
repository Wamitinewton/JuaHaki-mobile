package com.newton.auth.manager

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.newton.auth.workers.TokenRefreshWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRefreshManager
    @Inject
    constructor(
        private val workManager: WorkManager,
    ) {
        companion object {
            private const val TOKEN_REFRESH_INTERVAL_HOURS = 12L
            private const val FLEX_TIME_HOURS = 2L
            private const val INITIAL_BACKOFF_DELAY_MINUTES = 1L
            private const val TAG = "TokenRefreshManager"
        }

        /**
         * Starts periodic background token refresh
         * Uses KEEP policy to avoid duplicate work
         */

        fun startPeriodicTokenRefresh() {
            val constraints =
                Constraints
                    .Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .setRequiresStorageNotLow(true)
                    .build()

            val tokenRefreshWork =
                PeriodicWorkRequestBuilder<TokenRefreshWorker>(
                    repeatInterval = TOKEN_REFRESH_INTERVAL_HOURS,
                    repeatIntervalTimeUnit = TimeUnit.HOURS,
                    flexTimeInterval = FLEX_TIME_HOURS,
                    flexTimeIntervalUnit = TimeUnit.HOURS,
                ).setConstraints(constraints)
                    .setBackoffCriteria(
                        BackoffPolicy.EXPONENTIAL,
                        INITIAL_BACKOFF_DELAY_MINUTES,
                        TimeUnit.MINUTES,
                    ).addTag(TokenRefreshWorker.TAG)
                    .build()

            workManager.enqueueUniquePeriodicWork(
                TokenRefreshWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                tokenRefreshWork,
            )
        }

        fun stopPeriodicTokenRefresh() {
            workManager.cancelUniqueWork(TokenRefreshWorker.WORK_NAME)
        }

        /**
         * Forces immediate token refresh (one-time work)
         */
        fun forceTokenRefresh() {
            val immediateRefreshWork =
                OneTimeWorkRequestBuilder<TokenRefreshWorker>()
                    .setConstraints(
                        Constraints
                            .Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build(),
                    ).setBackoffCriteria(
                        BackoffPolicy.EXPONENTIAL,
                        INITIAL_BACKOFF_DELAY_MINUTES,
                        TimeUnit.MINUTES,
                    ).addTag("${TokenRefreshWorker.TAG}_immediate")
                    .build()

            workManager.enqueue(immediateRefreshWork)
        }

        /**
         * Observes the status of periodic token refresh work
         */
        fun observeTokenRefreshStatus(): Flow<Boolean> =
            workManager
                .getWorkInfosForUniqueWorkFlow(TokenRefreshWorker.WORK_NAME)
                .map { workInfos ->
                    workInfos.any { it.state == WorkInfo.State.RUNNING }
                }

        /**
         * Checks if periodic token refresh is scheduled
         */
        fun isTokenRefreshScheduled(): Flow<Boolean> =
            workManager
                .getWorkInfosForUniqueWorkFlow(TokenRefreshWorker.WORK_NAME)
                .map { workInfos ->
                    workInfos.any {
                        it.state == WorkInfo.State.ENQUEUED ||
                            it.state == WorkInfo.State.RUNNING
                    }
                }
    }
