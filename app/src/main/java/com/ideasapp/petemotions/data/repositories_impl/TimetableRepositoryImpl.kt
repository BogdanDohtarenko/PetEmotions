package com.ideasapp.petemotions.data.repositories_impl

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ideasapp.petemotions.data.db.TimetableDao
import com.ideasapp.petemotions.data.db.TimetableMapper
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.domain.repositories.TimetableRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimetableRepositoryImpl @Inject constructor(
    private val timetableDao: TimetableDao
) : TimetableRepository {

    override fun getTimetableList(): PagingSource<Int, TimetableItem> {
        return TimetablePagingSource(timetableDao)
    }

    override suspend fun addTimetableItem(newItem: TimetableItem) {
        val dbModel = TimetableMapper.entityToDbModel(newItem)
        try {
            Log.d("Timetable", "Inserting item $dbModel")
            timetableDao.addTimetableItem(dbModel)
            Log.d("Timetable", "Inserting done, error code 0")
        } catch (e: Exception) {
            Log.e("Timetable", "Error inserting item: ${e.localizedMessage}", e)
        }
    }

    override suspend fun deleteTimetableItem(oldItem: TimetableItem) {
        val dbModel = TimetableMapper.entityToDbModel(oldItem)
        timetableDao.deleteTimetableItem(dbModel.id)
    }

    class TimetablePagingSource @Inject constructor(
        private val timetableDao: TimetableDao
    ) : PagingSource<Int, TimetableItem>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TimetableItem> {
            val page = params.key ?: 0
            return try {
                val dbModelItems = timetableDao.getTimetableList()

                val items = dbModelItems.map {
                    TimetableMapper.dbModelToEntity(it)
                }

                LoadResult.Page(
                    data = items,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (items.isEmpty()) null else page + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, TimetableItem>): Int? {
            return state.anchorPosition?.let { position ->
                state.closestPageToPosition(position)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
            }
        }
    }
}