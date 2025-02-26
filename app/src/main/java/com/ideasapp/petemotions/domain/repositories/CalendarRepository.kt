package com.ideasapp.petemotions.domain.repositories

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import java.time.YearMonth

interface CalendarRepository {
    suspend fun getCalendarWithMood(
        yearMonth:YearMonth
    ): List<CalendarUiState.Date>

    suspend fun addDayItemInfo(
        dayItemInfo:DayItemInfo
    )
}
//TODO