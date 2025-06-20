package com.ideasapp.petemotions.domain.entity.calendar

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

data class DayItemInfo(
    @SerializedName("date")
    val date: Long = 1,
    @SerializedName("petId")
    var petId: Int = 0,
    @SerializedName("mood")
    val mood: Int? = null,
    @SerializedName("text")
    val text: String = "",
    @SerializedName("attributeNames")
    val attributeNames: List<String> = listOf<String>()
): Serializable {
    companion object {
        const val EMPTY = 0
        const val GOOD_MOOD = 1
        const val NORMAL_MOOD = 2
        const val BAD_MOOD = 3
    }
}