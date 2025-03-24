package com.ideasapp.petemotions.domain.use_case.calendar

import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import javax.inject.Inject

class AddDayAttributeUseCase @Inject constructor(
    private val repository: CalendarRepository,
) {
    suspend operator fun invoke(attributes: List<DayAttribute>) {
        repository.addDayAttribute(attributes)
    }
}