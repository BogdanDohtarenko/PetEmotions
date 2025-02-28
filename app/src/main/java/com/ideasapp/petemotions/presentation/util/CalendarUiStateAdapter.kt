package com.ideasapp.petemotions.presentation.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState

//class CalendarUiStateDateAdapter : TypeAdapter<CalendarUiState.Date>() {
//    override fun write(out: JsonWriter, value: CalendarUiState.Date) {
//        out.value(value.toString()) // Adjust based on how the value is stored
//    }
//
//    override fun read(reader: JsonReader): CalendarUiState.Date {
//        val stringValue = reader.nextString()
//        return CalendarUiState.Date(stringValue)
//    }
//}