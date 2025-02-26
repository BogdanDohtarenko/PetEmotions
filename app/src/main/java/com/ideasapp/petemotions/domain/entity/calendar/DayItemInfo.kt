package com.ideasapp.petemotions.domain.entity.calendar

import java.time.LocalDate

data class DayItemInfo(
    val id: Int = -1,
    val date: LocalDate? = null,
    val mood: String = EMPTY
){
    companion object {
        const val EMPTY = ""
        const val GOOD_MOOD = "g"
        const val NORMAL_MOOD = "n"
        const val BAD_MOOD = "b"
    }
}