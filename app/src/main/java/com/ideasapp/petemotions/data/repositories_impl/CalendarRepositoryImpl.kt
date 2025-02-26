package com.ideasapp.petemotions.data.repositories_impl

import android.app.Application
import android.util.Log
import com.ideasapp.petemotions.data.db.AppDatabase
import com.ideasapp.petemotions.data.db.DayInfoMapper
import com.ideasapp.petemotions.data.db.DayItemInfoDbModel
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.presentation.util.getDayOfMonthStartingFromMonday
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth

class CalendarRepositoryImpl(application: Application) : CalendarRepository {
    //TODO Hilt
    private val calendarListDao = AppDatabase.getInstance(application).CalendarListDao()

    override suspend fun getCalendarWithMood(
        yearMonth: YearMonth
    ): Flow<List<CalendarUiState.Date>> {
        return calendarListDao.getDayInfoList()
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

    private fun dayItemInfoDbModels(allMoodData : List<DayItemInfoDbModel>, yearMonth : YearMonth) : List<DayItemInfoDbModel> {
        val filteredData = allMoodData.filter {item->
            val date = LocalDate.ofEpochDay(item.date)
            date.year == yearMonth.year && date.monthValue == yearMonth.monthValue
        }
        return filteredData
    }

    override suspend fun addDayItemInfo(dayItemInfo: DayItemInfo) {
        calendarListDao.addItemDayInfo(DayInfoMapper.entityToDbModel(dayItemInfo))
    }
}