package com.ideasapp.petemotions.data.repositories_impl

import android.content.Context
import com.ideasapp.petemotions.data.dataStore.PetDataStore
import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.data.db.mappers.DayInfoMapper
import com.ideasapp.petemotions.data.db.dbModels.DayItemInfoDbModel
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.presentation.util.getDayOfMonthStartingFromMonday
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val calendarListDao: CalendarListDao,
) : CalendarRepository {

    private fun dayItemInfoDbModels(allMoodData: List<DayItemInfoDbModel>, yearMonth: YearMonth): List<DayItemInfoDbModel> {
        return allMoodData.filter { item ->
            val date = LocalDate.ofEpochDay(item.date)
            date.year == yearMonth.year && date.monthValue == yearMonth.monthValue
        }
    }

    override suspend fun getCalendar(yearMonth: YearMonth): Flow<List<CalendarUiState.Date>> {
        val allDates = yearMonth.getDayOfMonthStartingFromMonday().map { date ->
            val currDayInfo = DayItemInfo(date = date.toEpochDay())
            val isDateInMonth = date.monthValue == yearMonth.monthValue

            CalendarUiState.Date(
                dayOfMonth = if (isDateInMonth) "${date.dayOfMonth}" else "",
                isSelected = date.isEqual(LocalDate.now()) && isDateInMonth,
                dayInfoItem = currDayInfo
            )
        }
        return flowOf(allDates)
    }

    override suspend fun getMoodForPet(yearMonth : YearMonth, petId : Int) : Flow<List<CalendarUiState.Date>> {
        return calendarListDao.getDayInfoList(petId)
            .map { allMoodData ->
                val filteredData = dayItemInfoDbModels(allMoodData, yearMonth)

                yearMonth.getDayOfMonthStartingFromMonday().map { date ->

                    val currDayInfo = filteredData.find { it.date == date.toEpochDay() }
                        ?.let { DayInfoMapper.dbModelToEntity(it) } ?: DayItemInfo(date = date.toEpochDay())
                    val isDateInMonth = date.monthValue == yearMonth.monthValue

                    CalendarUiState.Date(
                        dayOfMonth = if (isDateInMonth) "${date.dayOfMonth}" else "",
                        isSelected = date.isEqual(LocalDate.now()) && isDateInMonth,
                        dayInfoItem = currDayInfo
                    )
                }
            }
    }

    override suspend fun addDayItemInfo(dayItemInfo: DayItemInfo) {
        calendarListDao.addItemDayInfo(DayInfoMapper.entityToDbModel(dayItemInfo))
    }

    //Pets
    override suspend fun getPetsList() : Flow<List<Pet>> {
        return PetDataStore.getPetsFlow(appContext)
    }
    override suspend fun addPet(pets : List<Pet>) {
        PetDataStore.savePets(context = appContext, pets = pets)
    }
}