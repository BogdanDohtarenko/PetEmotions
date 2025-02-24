package com.ideasapp.petemotions.domain.use_case.calendar

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import java.time.YearMonth

class GetSystemCalendar(
    private val repository: CalendarRepository,
    private val yearMonth: YearMonth
) {
    operator fun invoke(): List<CalendarUiState.Date> {
        return repository.getSystemCalendar(yearMonth)
    }
}