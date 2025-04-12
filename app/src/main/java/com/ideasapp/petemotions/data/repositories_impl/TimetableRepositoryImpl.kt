package com.ideasapp.petemotions.data.repositories_impl

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ideasapp.petemotions.data.alarm.cancelAlarm
import com.ideasapp.petemotions.data.alarm.saveTasks
import com.ideasapp.petemotions.data.alarm.setAlarm
import com.ideasapp.petemotions.data.db.dao.TimetableDao
import com.ideasapp.petemotions.data.db.mappers.TimetableMapper
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.domain.repositories.TimetableRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimetableRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val timetableDao: TimetableDao
) : TimetableRepository {

    override fun getTimetableList(): Flow<PagingData<TimetableItem>> {
        return getTimetableFlow()
    }

    override suspend fun addTimetableItem(newItem: TimetableItem) {
        val dbModel = TimetableMapper.entityToDbModel(newItem)
        try {
            timetableDao.addTimetableItem(dbModel)
            appContext.setAlarm(newItem)
            saveTasks(appContext, listOf(newItem))
            Log.d("Timetable", "Item inserted with ID: ${dbModel.id}")
        } catch (e: Exception) {
            Log.e("Timetable", "Error inserting item: ${e.localizedMessage}", e)
        }
    }

    override suspend fun deleteTimetableItem(oldItem: TimetableItem) {
        val dbModel = TimetableMapper.entityToDbModel(oldItem)
        appContext.cancelAlarm(oldItem)
        timetableDao.deleteTimetableItem(dbModel.id)
    }

    //provide Flow
    private fun getTimetableFlow(pageSize: Int = 20): Flow<PagingData<TimetableItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TimetablePagingSource(timetableDao) }
        ).flow
    }

    class TimetablePagingSource @Inject constructor(
        private val timetableDao: TimetableDao
    ) : PagingSource<Int, TimetableItem>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TimetableItem> {
            val page = params.key ?: 0
            val pageSize = params.loadSize

            return try {
                val offset = page * pageSize
                val dbModelItems = timetableDao.getTimetableListPaged(pageSize, offset)

                val items = dbModelItems.map {
                    TimetableMapper.dbModelToEntity(it)
                }

                val nextKey = if (items.isEmpty()) null else page + 1
                val prevKey = if (page == 0) null else page - 1

                LoadResult.Page(
                    data = items,
                    prevKey = prevKey,
                    nextKey = nextKey
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