package com.ideasapp.petemotions.domain.use_case.calendar

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import java.time.YearMonth

class GetCalendarWithMood(
    private val repository: CalendarRepository,
) {
    suspend operator fun invoke(yearMonth: YearMonth): List<CalendarUiState.Date> {
        return repository.getCalendarWithMood(yearMonth)
    }
}