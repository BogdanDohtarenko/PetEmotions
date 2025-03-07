package com.ideasapp.petemotions.domain.use_case.timetable

import androidx.paging.PagingSource
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.domain.repositories.TimetableRepository
import javax.inject.Inject


class AddTimetableItemUseCase @Inject constructor(
    private val repository: TimetableRepository,
) {
    operator fun invoke(newItem : TimetableItem) {
        return repository.addTimetableItem(newItem)
    }
}