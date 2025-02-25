package com.ideasapp.petemotions.data.repositories_impl

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayInfoItem
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.presentation.util.getDayOfMonthStartingFromMonday
import java.time.LocalDate
import java.time.YearMonth

object CalendarRepositoryImpl: CalendarRepository {
    //TODO do this in io thread

    override suspend fun getCalendarWithMood(
        yearMonth: YearMonth,
    ): List<CalendarUiState.Date> {
        return yearMonth.getDayOfMonthStartingFromMonday()
            .map { date ->
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
                        getMoodFromDatabaseByMonth(yearMonth)[date] ?: DayInfoItem()
                    } else {
                        DayInfoItem() //Fill with empty string for days outside the current month
                    }
                )
            // TODO Get  gray icon if not filled
            }
    }

    private fun getMoodFromDatabaseByMonth(yearMonth: YearMonth): Map<LocalDate, DayInfoItem> {
        //TODO add day info from db
        return mapOf(
            LocalDate.of(2025, 2, 1) to DayInfoItem(DayInfoItem.GOOD_MOOD),
            LocalDate.of(2025, 2, 14) to DayInfoItem(DayInfoItem.NORMAL_MOOD),
            LocalDate.of(2025, 2, 28) to DayInfoItem(DayInfoItem.BAD_MOOD)
        )
    }

}