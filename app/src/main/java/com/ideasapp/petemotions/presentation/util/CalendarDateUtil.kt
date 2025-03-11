package com.ideasapp.petemotions.presentation.util

import android.util.Log
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import com.google.gson.Gson
import com.ideasapp.petemotions.presentation.activity.MainActivity

object CalendarDateUtil {
    val daysOfWeek: Array<String>
        get() {
            val daysOfWeek = Array(7) { "" }

            for (dayOfWeek in DayOfWeek.entries) {
                val localizedDayName = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                daysOfWeek[dayOfWeek.value - 1] = localizedDayName
            }

            return daysOfWeek
        }
}

//A year-month in the ISO-8601 calendar system, such as 2007-12.
fun YearMonth.getDayOfMonthStartingFromMonday(): List<LocalDate> {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val firstMondayOfMonth = firstDayOfMonth.with(DayOfWeek.MONDAY)
    val firstDayOfNextMonth = firstDayOfMonth.plusMonths(1)

    return generateSequence(firstMondayOfMonth) { it.plusDays(1) }
        .takeWhile { it.isBefore(firstDayOfNextMonth) }
        .toList()
}

fun YearMonth.getDisplayName(): String {
    return "${month.getDisplayName(TextStyle.FULL, Locale.getDefault())} $year"
}


fun CalendarUiState.Date.toJson(): String {
    return Gson().toJson(this)
}

fun String.toCalendarUiStateDate(): CalendarUiState.Date? {
    return try {
        Gson().fromJson(this, CalendarUiState.Date::class.java)
    } catch (e: Exception) {
        Log.d(MainActivity.CALENDAR_LOG_TAG,"Failed to deserialize JSON: ${e.message}")
        null
    }
}