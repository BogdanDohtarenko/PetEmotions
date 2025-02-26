package com.ideasapp.petemotions.domain.entity.calendar

import java.time.LocalDate
import java.time.YearMonth

data class CalendarUiState(
    val yearMonth: YearMonth,
    val dates: List<Date>
) {
    companion object {
        val Init = CalendarUiState(
            yearMonth = YearMonth.now(),
            dates = emptyList()
        )
    }
    data class Date(
        val dayOfMonth: String,
        val isSelected: Boolean,
        val dayInfoItem: DayItemInfo
    ) {
        companion object {
            val Empty = Date("", false, DayItemInfo(LocalDate.of(2025, 2, 25).toEpochDay()))
        }
    }
}