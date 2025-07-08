package com.newton.juahaki.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.newton.auth.workers.TokenRefreshWorker
import com.newton.domain.repository.auth.AuthRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomWorkerFactory
@Inject
constructor(
    private val authRepository: AuthRepository,
) : WorkerFactory() {
    companion object {
        private const val TAG = "CustomWorkerFactory"
    }

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? =
        try {
            when (workerClassName) {
                TokenRefreshWorker::class.java.name -> {
                    Timber.d("$TAG: Creating TokenRefreshWorker")
                    TokenRefreshWorker(
                        context = appContext,
                        workerParameters = workerParameters,
                        authRepository = authRepository,
                    )
                }

                else -> {
                    Timber.w("$TAG: Unknown worker class: $workerClassName")
                    null // Return null to let the default factory handle it
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "$TAG: Failed to create worker: $workerClassName")
            null
        }
}
