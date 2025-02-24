package com.ideasapp.petemotions.domain.repositories

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import java.time.YearMonth

interface CalendarRepository {
    fun getSystemCalendar(yearMonth:YearMonth): List<CalendarUiState.Date>
}
//TODO