package com.ideasapp.petemotions.domain.repositories

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

interface CalendarRepository {
    suspend fun getCalendarWithMood(
        yearMonth: YearMonth
    ): Map<Int, Flow<List<CalendarUiState.Date>>>

    suspend fun addDayItemInfo(
        dayItemInfo:DayItemInfo
    )
}
