package com.ideasapp.petemotions.data.repositories_impl

import android.content.Context
import android.util.Log
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val calendarListDao: CalendarListDao,
) : CalendarRepository {
    //auto filling service
    override suspend fun autofillPreviousDay() {
            try {
                val today = LocalDate.now()
                val yesterday = today.minusDays(1)
                val dayBeforeYesterday = today.minusDays(2)

                val petsList = PetDataStore.getPetsFlow(appContext).first()

                Log.d("AutoFill", "petsList: $petsList")

                petsList.forEach { pet ->
                    try {
                        val allDays = calendarListDao.getDayInfoList(pet.id)

                        val dayBeforeYesterdayRecord = allDays.find {
                            LocalDate.ofEpochDay(it.date) == dayBeforeYesterday
                        }
                        Log.d("AutoFill", "dayBeforeYesterdayRecord: $dayBeforeYesterdayRecord")
                        val yesterdayRecord = allDays.find {
                            LocalDate.ofEpochDay(it.date) == yesterday
                        }
                        Log.d("AutoFill", "yesterdayRecord: $yesterdayRecord")
                        if (dayBeforeYesterdayRecord != null && yesterdayRecord == null) {
                            val newYesterdayRecord = dayBeforeYesterdayRecord.copy(
                                date = yesterday.toEpochDay(),
                                //TODO set all other fields to null
                            )
                            calendarListDao.addItemDayInfo(newYesterdayRecord)
                            Log.d("AutoFill", "Copied record for pet ${pet.id} from $dayBeforeYesterday to $yesterday")
                        }
                    } catch (e: Exception) {
                        Log.e("AutoFill", "Error processing pet ${pet.id}", e)
                    }
                }
            } catch (e: Exception) {
                Log.e("AutoFill", "Error in autofillPreviousDay", e)
            }
    }

    private fun dayItemInfoDbModels(allMoodData: List<DayItemInfoDbModel>,yearMonth: YearMonth): List<DayItemInfoDbModel> {
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
        return calendarListDao.getDayInfoFlowList(petId)
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