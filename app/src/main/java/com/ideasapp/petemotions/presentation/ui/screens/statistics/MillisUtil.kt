package com.ideasapp.petemotions.presentation.ui.screens.statistics

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun millisToLocalDateAndTime(millis: Long): Pair<LocalDate, LocalTime> {
    val instant = Instant.ofEpochMilli(millis)

    val zonedDateTime = instant.atZone(ZoneId.systemDefault())

    val localDate = zonedDateTime.toLocalDate()
    val localTime = zonedDateTime.toLocalTime()

    return Pair(localDate, localTime)
}

fun Pair<LocalDate, LocalTime>.toDateTimeString(): String {
    val (date, time) = this

    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val formattedDate = date.format(dateFormatter)
    val formattedTime = time.format(timeFormatter)

    return "$formattedDate, $formattedTime"
}
