package com.ideasapp.petemotions.data.repositories_impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ideasapp.petemotions.data.db.CalendarListDao
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.domain.repositories.TimetableRepository
import javax.inject.Inject

class TimetableRepositoryImpl @Inject constructor(
    private val calendarListDao : CalendarListDao
): TimetableRepository {

    override fun getTimetableList() : PagingSource<Int, TimetableItem> {
        return TimetablePagingSource()
    }

    override fun addTimetableItem(newItem : TimetableItem) {
        TODO("Not yet implemented")
    }

    override fun deleteTimetableItem(oldItem : TimetableItem) {
        TODO("Not yet implemented")
    }

    private class TimetablePagingSource : PagingSource<Int, TimetableItem>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TimetableItem> {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val items = mutableListOf<TimetableItem>()

            //TODO AMEND
            for (i in page * pageSize until 10) {
                items.add(TimetableItem(id = i, description = "Item $i", dateTime = "2025-03-05"))
            }

            return LoadResult.Page(
                data = items,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        }

        override fun getRefreshKey(state: PagingState<Int, TimetableItem>): Int? {
            return state.anchorPosition?.let { position ->
                state.closestPageToPosition(position)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
            }
        }
    }
}