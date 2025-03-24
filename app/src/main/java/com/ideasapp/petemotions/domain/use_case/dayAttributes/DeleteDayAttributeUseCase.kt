package com.ideasapp.petemotions.domain.use_case.dayAttributes

import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.domain.repositories.DayAttributesRepository
import javax.inject.Inject

class DeleteDayAttributeUseCase@Inject constructor(
    private val repository:DayAttributesRepository,
) {
    suspend operator fun invoke(attribute: DayAttribute) {
        repository.deleteDayAttribute(attribute)
    }
}