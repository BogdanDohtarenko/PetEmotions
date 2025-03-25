package com.ideasapp.petemotions.domain.use_case.dayAttributes

import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.domain.repositories.DayAttributesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDayAttributesUseCase  @Inject constructor(
    private val repository: DayAttributesRepository,
) {
    suspend operator fun invoke(): Flow<List<DayAttribute>> {
        return repository.getDayAttributes()
    }
}