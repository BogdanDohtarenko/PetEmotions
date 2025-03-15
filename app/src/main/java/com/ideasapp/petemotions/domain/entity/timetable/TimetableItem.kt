package com.ideasapp.petemotions.domain.entity.timetable

import java.time.LocalDate
import java.time.LocalDateTime

class TimetableItem(
    val id: Int = UNDEFINED_ID,
    val description: String = "",
    val dateTime: Long = System.currentTimeMillis()
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}