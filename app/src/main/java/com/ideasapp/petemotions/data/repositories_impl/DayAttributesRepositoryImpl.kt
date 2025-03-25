package com.ideasapp.petemotions.data.repositories_impl

import android.content.Context
import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.data.db.dao.DayAttributesDao
import com.ideasapp.petemotions.data.db.mappers.DayAttributeMapper
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.repositories.DayAttributesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DayAttributesRepositoryImpl @Inject constructor(
    private val dayAttributesListDao: DayAttributesDao,
): DayAttributesRepository {
    override suspend fun addDayAttribute(dayAttribute:DayAttribute) {
        dayAttributesListDao.addDayAttribute(DayAttributeMapper.entityToDbModel(dayAttribute))
    }

    override suspend fun deleteDayAttribute(dayAttribute:DayAttribute) {
        dayAttributesListDao.deleteDayAttribute(dayAttribute.id)
    }

    override suspend fun getDayAttributes(): Flow<List<DayAttribute>> {
        return dayAttributesListDao.getDayAttributeList()
            .map { dbModelList ->
                dbModelList.map { dbModel ->
                    DayAttributeMapper.dbModelToEntity(dbModel)
                }
            }
    }
}