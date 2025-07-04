package com.newton.data.remote

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
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
 * Executes a safe API call and emits [Resource] states with enhanced server error message extraction.
 *
 * @param apiCall The suspend function representing the API call.
 * @param errorHandler Optional lambda to provide custom error messages when server message is unavailable.
 * @return A [Flow] emitting [Resource.Loading], [Resource.Success], or [Resource.Error].
 */
fun <T> safeApiCall(
    apiCall: suspend () -> T,
    errorHandler: (Throwable) -> String = { it.localizedMessage ?: "Unknown error occurred" },
): Flow<Resource<T>> =
    flow {
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
                errorType = ErrorType.FATAL,
            ),
        )
        emit(Resource.Loading(false))
    }

/**
 * Handles exceptions by mapping them to a [Resource.Error] with server messages prioritized.
 */
private fun <T> handleException(
    e: Exception,
    errorHandler: (Throwable) -> String,
): Resource<T> =
    when (e) {
        is SocketTimeoutException, is TimeoutException -> {
            Resource.Error(
                message = "Request timed out. Please try again.",
                errorType = ErrorType.TIMEOUT,
            )
        }

        is ConnectException, is UnknownHostException -> {
            Resource.Error(
                message = "Couldn't connect to server. Please check your internet connection.",
                errorType = ErrorType.CONNECTIVITY,
            )
        }

        is HttpException -> {
            val errorResponse = handleHttpError(e)
            Resource.Error(
                message = errorResponse.message,
                errorType = errorResponse.errorType,
                httpCode = e.code(),
            )
        }

        is IOException -> {
            Resource.Error(
                message = "Network error: ${e.message ?: "Please check your internet connection"}",
                errorType = ErrorType.IO,
            )
        }

        else -> {
            val errorMessage = errorHandler(e)
            Resource.Error(
                message = "An unexpected error occurred: $errorMessage",
                errorType = ErrorType.UNKNOWN,
            )
        }
    }

/**
 * Processes HTTP errors with priority on server-provided error messages.
 */
private fun handleHttpError(error: HttpException): ErrorResponse {
    val serverMessage = extractServerErrorMessage(error)
    val code = error.code()

    return when (code) {
        400 ->
            ErrorResponse(
                message = serverMessage ?: "Invalid request: Please check your input",
                errorType = ErrorType.CLIENT_ERROR,
            )
        401 ->
            ErrorResponse(
                message = serverMessage ?: "Unauthorized: Please log in again",
                errorType = ErrorType.AUTHENTICATION,
            )
        403 ->
            ErrorResponse(
                message = serverMessage ?: "Access denied: You don't have permission to access this resource",
                errorType = ErrorType.AUTHORIZATION,
            )
        404 ->
            ErrorResponse(
                message = "Resource not found",
                errorType = ErrorType.NOT_FOUND,
            )
        408 ->
            ErrorResponse(
                message = serverMessage ?: "Request timeout: Please try again",
                errorType = ErrorType.TIMEOUT,
            )
        409 ->
            ErrorResponse(
                message = serverMessage ?: "Conflict: The request couldn't be completed due to a conflict",
                errorType = ErrorType.CONFLICT,
            )
        422 ->
            ErrorResponse(
                message = serverMessage ?: "Validation failed: Please check your input",
                errorType = ErrorType.VALIDATION,
            )
        429 ->
            ErrorResponse(
                message = serverMessage ?: "Too many requests: Please try again later",
                errorType = ErrorType.RATE_LIMIT,
            )
        in 500..599 ->
            ErrorResponse(
                message = serverMessage ?: "Server error: Please try again later",
                errorType = ErrorType.SERVER_ERROR,
            )
        else ->
            ErrorResponse(
                message = serverMessage ?: "Network error (Code: $code): ${error.message()}",
                errorType = ErrorType.HTTP_ERROR,
            )
    }
}

/**
 * Extracts error message from server response body using multiple parsing strategies.
 */
private fun extractServerErrorMessage(error: HttpException): String? {
    return try {
        val errorBody = error.response()?.errorBody()?.string()

        if (errorBody.isNullOrBlank()) {
            return null
        }

        parseAsApiResponse(errorBody)
            ?: parseAsGenericError(errorBody)
            ?: parseAsRawMessage(errorBody)
    } catch (e: Exception) {
        null
    }
}

/**
 * Attempts to parse error body as ApiResponse<Any> format.
 */
private fun parseAsApiResponse(errorBody: String): String? =
    try {
        val gson = Gson()
        val apiResponse = gson.fromJson(errorBody, ApiResponse::class.java)
        apiResponse?.message?.takeIf { it.isNotBlank() }
    } catch (e: JsonSyntaxException) {
        null
    } catch (e: Exception) {
        null
    }

/**
 * Attempts to parse error body as a generic error object with common error fields.
 */
private fun parseAsGenericError(errorBody: String): String? {
    return try {
        val gson = Gson()
        val errorMap = gson.fromJson(errorBody, Map::class.java)

        val messageFields = listOf("message", "error", "error_description", "detail", "msg")

        messageFields.forEach { field ->
            val message = errorMap?.get(field)?.toString()
            if (!message.isNullOrBlank()) {
                return message
            }
        }

        null
    } catch (e: Exception) {
        null
    }
}

/**
 * Attempts to extract message from raw error body (for plain text responses).
 */
private fun parseAsRawMessage(errorBody: String): String? =
    errorBody
        .takeIf {
            it.isNotBlank() &&
                it.length <= 500 &&
                !it.contains("<!DOCTYPE") &&
                // Not HTML
                !it.contains("<html") // Not HTML
        }?.trim()

data class ErrorResponse(
    val message: String,
    val errorType: ErrorType,
)
