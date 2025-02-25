package com.ideasapp.petemotions.data.repositories_impl

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayInfoItem
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.presentation.util.getDayOfMonthStartingFromMonday
import java.time.LocalDate
import java.time.YearMonth

object CalendarRepositoryImpl: CalendarRepository {

    override suspend fun getCalendarWithMood(
        yearMonth: YearMonth,
    ): List<CalendarUiState.Date> {
        return yearMonth.getDayOfMonthStartingFromMonday()
            .map { date ->
                val currDayInfo =
                    getMoodFromDatabaseByMonth(yearMonth).find {it.date == date}
                        ?: DayInfoItem(LocalDate.of(2025, 2, 25))
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
                        DayInfoItem(LocalDate.of(2025, 2, 25))
                    }
                )
            }
    }

    private fun getMoodFromDatabaseByMonth(yearMonth: YearMonth): List<DayInfoItem> {
        //TODO add day info from db

        //TODO END HERE
        return listOf(
            DayInfoItem(LocalDate.of(2025, 2, 1), DayInfoItem.GOOD_MOOD),
            DayInfoItem(LocalDate.of(2025, 2, 2), DayInfoItem.GOOD_MOOD),
            DayInfoItem(LocalDate.of(2025, 2, 10), DayInfoItem.GOOD_MOOD),
        )
        //val resultList = dao.getList.map
        //return resultList
    }

}