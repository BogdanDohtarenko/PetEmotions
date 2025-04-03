package com.ideasapp.petemotions.domain.use_case.calendar

import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import javax.inject.Inject

class AutofillPreviousDayUseCase @Inject constructor(
    private val repository:CalendarRepository,
) {
    suspend operator fun invoke(): Boolean {
        repository.autofillPreviousDay()
    }
}