package com.ideasapp.petemotions.data.repositories_impl

import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.data.db.mappers.DayInfoMapper
import com.ideasapp.petemotions.data.db.dbModels.DayItemInfoDbModel
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.presentation.util.getDayOfMonthStartingFromMonday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val calendarListDao: CalendarListDao
) : CalendarRepository {

    override suspend fun getCalendarWithMood(
        yearMonth: YearMonth
    ): Map<Int, Flow<List<CalendarUiState.Date>>> {
        val moodDataFlow = calendarListDao.getDayInfoList()
        val petDataMap = mutableMapOf<Int, MutableList<DayItemInfoDbModel>>()

        moodDataFlow.collect { allMoodData ->
            allMoodData.forEach { moodData ->
                petDataMap.getOrPut(moodData.petId) { mutableListOf() }.add(moodData)
            }
        }
        return petDataMap.mapValues { (petId, moodList) ->
            flow {
                val filteredData = dayItemInfoDbModels(moodList, yearMonth)

                val dates = yearMonth.getDayOfMonthStartingFromMonday().map { date ->
                    val currDayInfo = filteredData.find { it.date == date.toEpochDay() }
                        ?.let { DayInfoMapper.dbModelToEntity(it) } ?: DayItemInfo(date = date.toEpochDay(), petId = -1)
                    val isDateInMonth = date.monthValue == yearMonth.monthValue

                    CalendarUiState.Date(
                        dayOfMonth = if (isDateInMonth) "${date.dayOfMonth}" else "",
                        isSelected = date.isEqual(LocalDate.now()) && isDateInMonth,
                        dayInfoItem = currDayInfo
                    )
                }
                emit(dates)
            }.flowOn(Dispatchers.IO)
        }.toMap()
    }
    private fun dayItemInfoDbModels(allMoodData: List<DayItemInfoDbModel>, yearMonth: YearMonth): List<DayItemInfoDbModel> {
        return allMoodData.filter { item ->
            val date = LocalDate.ofEpochDay(item.date)
            date.year == yearMonth.year && date.monthValue == yearMonth.monthValue
        }
    }

    override suspend fun addDayItemInfo(dayItemInfo: DayItemInfo) {
        calendarListDao.addItemDayInfo(DayInfoMapper.entityToDbModel(dayItemInfo))
    }
}