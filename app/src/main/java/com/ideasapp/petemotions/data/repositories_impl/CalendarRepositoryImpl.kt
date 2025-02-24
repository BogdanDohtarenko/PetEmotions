package com.ideasapp.petemotions.data.repositories_impl

import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayInfoItem
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.presentation.util.getDayOfMonthStartingFromMonday
import java.time.LocalDate
import java.time.YearMonth

object CalendarRepositoryImpl: CalendarRepository {

    override fun getSystemCalendar(yearMonth:YearMonth): List<CalendarUiState.Date> {
        return yearMonth.getDayOfMonthStartingFromMonday()
            .map { date ->
                CalendarUiState.Date(
                    dayOfMonth = if (date.monthValue == yearMonth.monthValue) {
                        "${date.dayOfMonth}"
                    } else {
                        "" //Fill with empty string for days outside the current month
                    },
                    isSelected = date.isEqual(LocalDate.now()) && date.monthValue == yearMonth.monthValue,
                    DayInfoItem()
                ) //TODO add day info from db
            }
    }

}