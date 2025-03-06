package com.ideasapp.petemotions.domain.entity.timetable

class TimetableItem(
    val id: Int = -1,
    val description: String,
    val dateTime: String = "12:00, 9 april" //TODO DateTime Format
) {}