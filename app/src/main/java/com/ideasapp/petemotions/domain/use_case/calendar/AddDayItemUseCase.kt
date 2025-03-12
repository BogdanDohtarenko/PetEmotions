package com.ideasapp.petemotions.domain.use_case.calendar

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import java.time.YearMonth
import javax.inject.Inject

class AddDayItemUseCase  @Inject constructor(
    private val repository:CalendarRepository,
) {
    suspend operator fun invoke(dayItemInfo: DayItemInfo) {
        repository.addDayItemInfo(dayItemInfo)
    }
}
