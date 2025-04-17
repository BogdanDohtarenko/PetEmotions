package com.ideasapp.petemotions.domain.use_case.pets

import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import javax.inject.Inject

class DeleteAllPetData @Inject constructor(
    private val repository:CalendarRepository,
) {
    suspend operator fun invoke(petId: Int) {
        repository.deleteAllPetData(petId)
    }
}
