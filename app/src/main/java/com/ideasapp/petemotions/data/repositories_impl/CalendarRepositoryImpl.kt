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
import java.time.LocalDate
import java.time.YearMonth

class CalendarRepositoryImpl(application:Application): CalendarRepository {

    //TODO HILT
    private val calendarListDao = AppDatabase.getInstance(application).CalendarListDao()

    override suspend fun getCalendarWithMood(
        yearMonth: YearMonth,
    ): List<CalendarUiState.Date> {
        return yearMonth.getDayOfMonthStartingFromMonday()
            .map { date ->
                val currDayInfo =
                    DayInfoMapper.dbModelToEntity(
                        getMoodFromDatabaseByMonth(yearMonth)
                            .find {it.date == date.toEpochDay() } ?: DayItemInfoDbModel(
                                date = LocalDate.of(2025, 2, 25).toEpochDay()
                            )
                    )
                val isDateInMonth = date.monthValue == yearMonth.monthValue
                CalendarUiState.Date(
                    dayOfMonth = if (isDateInMonth) {
                        "${date.dayOfMonth}"
                    } else {
                        "" //Fill with empty string for days outside the current month
                    },
                    isSelected = date.isEqual(LocalDate.now()) && isDateInMonth,
                    dayInfoItem = if (isDateInMonth) {
                        //take mood from db
                        currDayInfo
                    } else {
                        //Fill with empty string for days outside the current month
                        DayItemInfo(LocalDate.of(2025, 2, 25).toEpochDay())
                    }
                )
            }
    }

    //NOT SAFE ID is AUTO INCREMENT WHEN WE CREATE NEW ITEM it can conflict
    override suspend fun addDayItemInfo(dayItemInfo:DayItemInfo) {
        calendarListDao.addItemDayInfo(DayInfoMapper.entityToDbModel(dayItemInfo))
        Log.d("Calendar", "Added new item: $dayItemInfo")
    }

    private suspend fun getMoodFromDatabaseByMonth(yearMonth: YearMonth): List<DayItemInfoDbModel> {
        val listAllDates = calendarListDao.getDayInfoList()
        return listAllDates.filter {
            val date = LocalDate.ofEpochDay(it.date)
            date.year == yearMonth.year && date.monthValue == yearMonth.monthValue
        }
    }

}