package com.ideasapp.petemotions.domain.entity.calendar

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate
import java.time.YearMonth

data class CalendarUiState(
    @SerializedName("yearMonth")
    val yearMonth: YearMonth = YearMonth.now(),
    @SerializedName("dates")
    val dates: List<Date> = emptyList()
): Serializable {

    data class Date(
        @SerializedName("dayOfMonth")
        val dayOfMonth: String = "",
        @SerializedName("isSelected")
        val isSelected: Boolean = false,
        @SerializedName("dayInfoItem")
        val dayInfoItem: DayItemInfo = DayItemInfo(0, 0)
    ): Serializable {

        companion object {
            val Empty = Date("", false, DayItemInfo(0, petId = -1))
        }

    }

    companion object {
        val Init = CalendarUiState(
            yearMonth = YearMonth.now(),
            dates = emptyList()
        )
    }
}