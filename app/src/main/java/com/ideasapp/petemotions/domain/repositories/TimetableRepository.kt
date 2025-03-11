package com.ideasapp.petemotions.domain.repositories

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import kotlinx.coroutines.flow.Flow

interface TimetableRepository {
    fun getTimetableList(): Flow<PagingData<TimetableItem>>
    suspend fun addTimetableItem(newItem: TimetableItem)
    suspend fun deleteTimetableItem(oldItem: TimetableItem)
}