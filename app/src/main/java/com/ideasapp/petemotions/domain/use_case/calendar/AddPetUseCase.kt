package com.ideasapp.petemotions.domain.use_case.calendar

import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import javax.inject.Inject

class AddPetUseCase @Inject constructor(
    private val repository: CalendarRepository,
) {
    suspend operator fun invoke(pets: List<Pet>) {
        repository.addPet(pets)
    }
}
