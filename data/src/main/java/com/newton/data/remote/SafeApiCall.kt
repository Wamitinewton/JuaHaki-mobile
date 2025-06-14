package com.newton.data.remote

import com.newton.core.enums.ErrorType
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.cancellation.CancellationException

/**
 * Executes a safe API call and emits [Resource] states.
 *
 * @param apiCall The suspend function representing the API call.
 * @param errorHandler Optional lambda to provide custom error messages.
 * @return A [Flow] emitting [Resource.Loading], [Resource.Success], or [Resource.Error].
 */
fun <T> safeApiCall(
    apiCall: suspend () -> T,
    errorHandler: (Throwable) -> String = { it.localizedMessage ?: "Unknown error occurred" }
): Flow<Resource<T>> = flow {
    emit(Resource.Loading(true))
    try {
        val response = apiCall()
        emit(Resource.Success(data = response))
    } catch (e: CancellationException) {
        emit(Resource.Loading(false))
        throw e
    } catch (e: Exception) {
        emit(handleException(e, errorHandler))
    } finally {
        emit(Resource.Loading(false))
    }
}.catch { e ->
    if (e is CancellationException) throw e
    emit(
        Resource.Error(
            message = "Fatal error: ${e.message ?: "Unknown error"}",
            errorType = ErrorType.FATAL
        )
    )
    emit(Resource.Loading(false))
}

/**
 * Handles exceptions by mapping them to a [Resource.Error] with an appropriate error message and type.
 */
private fun <T> handleException(
    e: Exception,
    errorHandler: (Throwable) -> String
): Resource<T> = when (e) {
    is SocketTimeoutException, is TimeoutException -> {
        Resource.Error(
            message = "Request timed out. Please try again.",
            errorType = ErrorType.TIMEOUT
        )
    }

    is ConnectException, is UnknownHostException -> {
        Resource.Error(
            message = "Couldn't connect to server. Please check your internet connection.",
            errorType = ErrorType.CONNECTIVITY
        )
    }

    is HttpException -> {
        val errorResponse = handleHttpError(e)
        Resource.Error(
            message = errorResponse.message,
            errorType = errorResponse.errorType,
            httpCode = e.code()
        )
    }

    is IOException -> {
        Resource.Error(
            message = "Network error: ${e.message ?: "Please check your internet connection"}",
            errorType = ErrorType.IO
        )
    }

    else -> {
        val errorMessage = errorHandler(e)
        Resource.Error(
            message = "An unexpected error occurred: $errorMessage",
            errorType = ErrorType.UNKNOWN
        )
    }
}

/**
 * Processes HTTP errors and returns an appropriate error response.
 */
private fun handleHttpError(error: HttpException): ErrorResponse {
    return when (val code = error.code()) {
        400 -> ErrorResponse("Invalid request: Please check your input", ErrorType.CLIENT_ERROR)
        401 -> ErrorResponse("Unauthorized: Please log in again", ErrorType.AUTHENTICATION)
        403 -> ErrorResponse(
            "Access denied: You don't have permission to access this resource",
            ErrorType.AUTHORIZATION
        )

        404 -> ErrorResponse("Resource not found", ErrorType.NOT_FOUND)
        408 -> ErrorResponse("Request timeout: Please try again", ErrorType.TIMEOUT)
        409 -> ErrorResponse(
            "Conflict: The request couldn't be completed due to a conflict",
            ErrorType.CONFLICT
        )

        422 -> ErrorResponse("Validation failed: Please check your input", ErrorType.VALIDATION)
        429 -> ErrorResponse("Too many requests: Please try again later", ErrorType.RATE_LIMIT)
        in 500..599 -> ErrorResponse("Server error: Please try again later", ErrorType.SERVER_ERROR)
        else -> ErrorResponse(
            "Network error (Code: $code): ${error.message()}",
            ErrorType.HTTP_ERROR
        )
    }
}

/**
 * Represents an error response for HTTP errors.
 */
data class ErrorResponse(
    val message: String,
    val errorType: ErrorType
)