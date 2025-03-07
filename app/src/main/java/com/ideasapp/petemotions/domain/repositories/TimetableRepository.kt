package com.ideasapp.petemotions.domain.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem

interface TimetableRepository {
    fun getTimetableList(): PagingSource<Int, TimetableItem>
    fun addTimetableItem(newItem: TimetableItem)
    fun deleteTimetableItem(oldItem: TimetableItem)
}