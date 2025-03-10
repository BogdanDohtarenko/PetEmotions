package com.ideasapp.petemotions.domain.entity.timetable

class TimetableItem(
    val id: Int = UNDEFINED_ID,
    val description: String = "",
    val dateTime: String = "12:00, 9 april" //TODO DateTime Format
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}