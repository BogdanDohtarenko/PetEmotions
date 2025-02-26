package com.ideasapp.petemotions.domain.entity.calendar

import java.time.LocalDate

data class DayItemInfo(
    val date: Long,
    val mood: String = EMPTY
){
    companion object {
        const val EMPTY = ""
        const val GOOD_MOOD = "g"
        const val NORMAL_MOOD = "n"
        const val BAD_MOOD = "b"
        //TODO Add const date
    }
}