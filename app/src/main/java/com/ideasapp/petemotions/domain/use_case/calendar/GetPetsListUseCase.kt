package com.ideasapp.petemotions.domain.use_case.calendar

import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPetsListUseCase @Inject constructor(
private val repository: CalendarRepository,
) {
    suspend operator fun invoke(): Flow<List<Pet>> {
        return repository.getPetsList()
    }
}
