package com.ideasapp.petemotions.domain.use_case.calendar

import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDayAttributesUseCase  @Inject constructor(
    private val repository: CalendarRepository,
) {
    suspend operator fun invoke(): Flow<List<DayAttribute>> {
        return repository.getDayAttributes()
    }
}