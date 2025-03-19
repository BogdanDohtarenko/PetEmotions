package com.ideasapp.petemotions.domain.entity.calendar

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

data class DayItemInfo(
    @SerializedName("date")
    val date: Long = 1,
    @SerializedName("petId")
    val petId: Int = 0,
    @SerializedName("mood")
    val mood: Int = EMPTY,
): Serializable {
    companion object {
        const val EMPTY = 0
        const val GOOD_MOOD = 3
        const val NORMAL_MOOD = 2
        const val BAD_MOOD = 1
    }
}