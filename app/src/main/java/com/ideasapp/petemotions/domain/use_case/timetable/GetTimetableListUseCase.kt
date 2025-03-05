package com.ideasapp.petemotions.domain.use_case.timetable

import androidx.paging.PagingSource
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.domain.repositories.TimetableRepository
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import javax.inject.Inject

class GetTimetableListUseCase @Inject constructor(
    private val repository: TimetableRepository,
) {
    operator fun invoke(): PagingSource<Int, TimetableItem> {
        return repository.getTimetableList()
    }
}