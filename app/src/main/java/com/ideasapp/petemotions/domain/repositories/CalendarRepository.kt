package com.ideasapp.petemotions.domain.repositories

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

interface CalendarRepository {
    suspend fun getCalendar(
        yearMonth: YearMonth,
    ): Flow<List<CalendarUiState.Date>>

    suspend fun getMoodForPet(
        yearMonth : YearMonth,
        petId: Int
    ): Flow<List<CalendarUiState.Date>>
    suspend fun addDayItemInfo(
        dayItemInfo:DayItemInfo
    )

    suspend fun getPetsList(): Flow<List<Pet>>
    suspend fun addPet(pets: List<Pet>)

    suspend fun autofillPreviousDay()
}
