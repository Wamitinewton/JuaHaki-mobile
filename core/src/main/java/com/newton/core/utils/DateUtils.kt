package com.newton.core.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    private val apiDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    /**
     * Converts LocalDate to String
     */
    @SuppressLint("NewApi")
    fun LocalDate.toApiString(): String {
        val calendar =
            Calendar.getInstance().apply {
                set(year, monthValue - 1, dayOfMonth)
            }
        return apiDateFormatter.format(calendar.time)
    }

    /**
     * Gets today's date in API format
     */
    fun getTodayApiFormat(): String = apiDateFormatter.format(Date())

    @SuppressLint("NewApi")
    fun String.toLocalDate(): LocalDate? {
        return try {
            val date = apiDateFormatter.parse(this) ?: return null
            val calendar = Calendar.getInstance().apply { time = date }
            LocalDate.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
            )
        } catch (e: Exception) {
            null
        }
    }
}
