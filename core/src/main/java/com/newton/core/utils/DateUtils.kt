package com.newton.core.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    // Date formatters
    private val apiDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val timestampFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.US)
    private val readableDateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    private val readableTimeFormatter = SimpleDateFormat("h:mm a", Locale.US)
    private val readableDateTimeFormatter = SimpleDateFormat("MMM dd, yyyy 'at' h:mm a", Locale.US)
    private val shortDateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    private val shortTimeFormatter = SimpleDateFormat("HH:mm", Locale.US)

    fun Calendar.toApiString(): String = apiDateFormatter.format(this.time)

    @SuppressLint("NewApi")
    fun java.time.LocalDate.toApiString(): String {
        val calendar =
            Calendar.getInstance().apply {
                set(year, monthValue - 1, dayOfMonth)
            }
        return apiDateFormatter.format(calendar.time)
    }

    fun getTodayApiFormat(): String = apiDateFormatter.format(Date())

    fun String.toCalendar(): Calendar? {
        return try {
            val date = apiDateFormatter.parse(this) ?: return null
            Calendar.getInstance().apply { time = date }
        } catch (e: Exception) {
            null
        }
    }

    @SuppressLint("NewApi")
    fun String.toLocalDate(): java.time.LocalDate? {
        return try {
            val date = apiDateFormatter.parse(this) ?: return null
            val calendar = Calendar.getInstance().apply { time = date }
            java.time.LocalDate.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
            )
        } catch (e: Exception) {
            null
        }
    }

    fun String.parseTimestamp(): Calendar? {
        return try {
            val normalizedTimestamp =
                if (this.contains('.')) {
                    val parts = this.split('.')
                    val wholePart = parts[0]
                    val fractionalPart = parts[1].padEnd(6, '0').take(6)
                    "$wholePart.$fractionalPart"
                } else {
                    this
                }

            val date = timestampFormatter.parse(normalizedTimestamp) ?: return null
            Calendar.getInstance().apply { time = date }
        } catch (e: Exception) {
            null
        }
    }

    fun String.toReadableDate(): String {
        val calendar = parseTimestamp()
        return if (calendar != null) {
            readableDateFormatter.format(calendar.time)
        } else {
            this
        }
    }

    /**
     * Formats timestamp to readable time: "4:52 PM"
     */
    fun String.toReadableTime(): String {
        val calendar = parseTimestamp()
        return if (calendar != null) {
            readableTimeFormatter.format(calendar.time)
        } else {
            this
        }
    }

    fun String.toReadableDateTime(): String {
        val calendar = parseTimestamp()
        return if (calendar != null) {
            readableDateTimeFormatter.format(calendar.time)
        } else {
            this
        }
    }

    fun String.toShortDate(): String {
        val calendar = parseTimestamp()
        return if (calendar != null) {
            shortDateFormatter.format(calendar.time)
        } else {
            this
        }
    }

    fun String.toShortTime(): String {
        val calendar = parseTimestamp()
        return if (calendar != null) {
            shortTimeFormatter.format(calendar.time)
        } else {
            this
        }
    }

    fun Calendar.toReadableDate(): String = readableDateFormatter.format(this.time)

    fun Calendar.toReadableTime(): String = readableTimeFormatter.format(this.time)

    fun Calendar.toReadableDateTime(): String = readableDateTimeFormatter.format(this.time)

    fun String.isToday(): Boolean {
        val calendar = parseTimestamp() ?: return false
        val today = Calendar.getInstance()

        return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
    }

    fun String.isYesterday(): Boolean {
        val calendar = parseTimestamp() ?: return false
        val yesterday =
            Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -1)
            }

        return calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)
    }

    fun String.toRelativeDate(): String =
        when {
            isToday() -> "Today"
            isYesterday() -> "Yesterday"
            else -> toReadableDate()
        }

    fun String.toTimeAgo(): String {
        val calendar = parseTimestamp() ?: return this
        val now = Calendar.getInstance()
        val diffInMillis = now.timeInMillis - calendar.timeInMillis

        val seconds = diffInMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days > 0 -> "${days}d ago"
            hours > 0 -> "${hours}h ago"
            minutes > 0 -> "${minutes}m ago"
            else -> "Just now"
        }
    }

    fun String.toQuizDate(): String =
        when {
            isToday() -> "Today"
            isYesterday() -> "Yesterday"
            else -> toReadableDate()
        }

    fun getCurrentTimestamp(): String = timestampFormatter.format(Date())

    fun String.isAfter(other: String): Boolean {
        val thisCalendar = parseTimestamp()
        val otherCalendar = other.parseTimestamp()

        return if (thisCalendar != null && otherCalendar != null) {
            thisCalendar.after(otherCalendar)
        } else {
            false
        }
    }

    fun String.isBefore(other: String): Boolean {
        val thisCalendar = parseTimestamp()
        val otherCalendar = other.parseTimestamp()

        return if (thisCalendar != null && otherCalendar != null) {
            thisCalendar.before(otherCalendar)
        } else {
            false
        }
    }
}
