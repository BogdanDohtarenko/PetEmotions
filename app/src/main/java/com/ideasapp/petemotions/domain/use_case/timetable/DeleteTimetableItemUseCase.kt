package com.ideasapp.petemotions.domain.use_case.timetable

import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.domain.repositories.TimetableRepository
import javax.inject.Inject

class DeleteTimetableItemUseCase @Inject constructor(
    private val repository: TimetableRepository,
) {
    operator fun invoke(oldItem: TimetableItem){
        return repository.deleteTimetableItem(oldItem)
    }
}