package com.ideasapp.petemotions.data.repositories_impl

import android.util.Log
import com.ideasapp.petemotions.data.db.dao.DayAttributesDao
import com.ideasapp.petemotions.data.db.mappers.DayAttributeMapper
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.repositories.DayAttributesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DayAttributesRepositoryImpl @Inject constructor(
    private val dayAttributesListDao: DayAttributesDao,
): DayAttributesRepository {
    override suspend fun addDayAttribute(dayAttribute:DayAttribute) {
        Log.d("Calendar", "@DayAttributesRepositoryImpl adding dayAttribute $dayAttribute")
        dayAttributesListDao.addDayAttribute(DayAttributeMapper.entityToDbModel(dayAttribute))
    }

    override suspend fun deleteDayAttribute(dayAttribute:DayAttribute) {
        dayAttributesListDao.deleteDayAttribute(dayAttribute.id)
    }

    override suspend fun getDayAttributes(): Flow<List<DayAttribute>> {
        return dayAttributesListDao.getDayAttributeFlowList()
            .map { dbModelList ->
                dbModelList.map { dbModel ->
                    DayAttributeMapper.dbModelToEntity(dbModel)
                }
            }
    }
}