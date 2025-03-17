package com.ideasapp.petemotions.domain.use_case.calendar

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import javax.inject.Inject

class GetCalendarWithMood @Inject constructor(
    private val repository: CalendarRepository,
) {
    suspend operator fun invoke(yearMonth: YearMonth): Map<Int, Flow<List<CalendarUiState.Date>>> {
        return repository.getCalendarWithMood(yearMonth)
    }
}