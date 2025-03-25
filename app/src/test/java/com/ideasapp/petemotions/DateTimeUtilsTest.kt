package com.ideasapp.petemotions

import com.ideasapp.petemotions.presentation.util.millisToLocalDateAndTime
import com.ideasapp.petemotions.presentation.util.toDateTimeString
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateTimeUtilsTest {

    @Test
    fun `millisToLocalDateAndTime should convert milliseconds into LocalDate and LocalTime`() {
        val millis = 1679759400000L // Equivalent to "2023-03-25T14:30:00" in UTC
        val expectedZone = ZoneId.systemDefault()
        val expectedZonedDateTime = Instant.ofEpochMilli(millis).atZone(expectedZone)
        val expectedDate = expectedZonedDateTime.toLocalDate()
        val expectedTime = expectedZonedDateTime.toLocalTime()

        val (localDate, localTime) = millisToLocalDateAndTime(millis)

        assertEquals(expectedDate, localDate)
        assertEquals(expectedTime, localTime)
    }

    @Test
    fun `toDateTimeString should format LocalDate and LocalTime to dd_MM_yyyy and HH_mm`() {
        val localDate = LocalDate.of(2023, 3, 25)
        val localTime = LocalTime.of(14, 30)
        val pair = Pair(localDate, localTime)

        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val expectedFormattedDate = localDate.format(dateFormatter)
        val expectedFormattedTime = localTime.format(timeFormatter)
        val expectedString = "$expectedFormattedDate, $expectedFormattedTime"

        val formattedString = pair.toDateTimeString()

        assertEquals(expectedString, formattedString)
    }
}