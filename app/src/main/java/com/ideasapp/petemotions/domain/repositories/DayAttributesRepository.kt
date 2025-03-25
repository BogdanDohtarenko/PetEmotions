package com.ideasapp.petemotions.domain.repositories

import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import kotlinx.coroutines.flow.Flow

interface DayAttributesRepository {
    suspend fun addDayAttribute(dayAttribute: DayAttribute)
    suspend fun deleteDayAttribute(dayAttribute: DayAttribute)
    suspend fun getDayAttributes():Flow<List<DayAttribute>>
}