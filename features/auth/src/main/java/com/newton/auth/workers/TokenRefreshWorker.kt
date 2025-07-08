package com.newton.auth.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.newton.core.utils.Resource
import com.newton.domain.models.auth.RefreshTokenData
import com.newton.domain.repository.auth.AuthRepository
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class TokenRefreshWorker
@AssistedInject
constructor(
    context: Context,
    workerParameters: WorkerParameters,
    private val authRepository: AuthRepository,
) : CoroutineWorker(context, workerParameters) {
    companion object {
        const val WORK_NAME = "token_refresh_work"
        const val TAG = "TokenRefreshWorker"
        const val MAX_RETRY_ATTEMPTS = 3
    }

    override suspend fun doWork(): Result {
        return try {
            val refreshToken = authRepository.getRefreshToken()
            if (refreshToken == null) {
                return Result.failure()
            }

            val result = authRepository.refreshToken(RefreshTokenData(refreshToken)).first()

            when (result) {
                is Resource.Success -> {
                    Result.success()
                }

                is Resource.Error -> {
                    if (runAttemptCount < MAX_RETRY_ATTEMPTS) {
                        Result.retry()
                    } else {
                        Result.failure()
                    }
                }

                is Resource.Loading -> {
                    Result.retry()
                }
            }
        } catch (e: IllegalStateException) {
            Result.failure()
        } catch (e: Exception) {
            if (runAttemptCount < MAX_RETRY_ATTEMPTS) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}
