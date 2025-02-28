package com.ideasapp.petemotions.domain.entity.calendar

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

data class DayItemInfo(
    @SerializedName("date")
    val date: Long = 1,
    @SerializedName("mood")
    val mood: String = EMPTY
): Serializable {
    companion object {
        const val EMPTY = ""
        const val GOOD_MOOD = "g"
        const val NORMAL_MOOD = "n"
        const val BAD_MOOD = "b"
        //TODO Add const date
    }
}