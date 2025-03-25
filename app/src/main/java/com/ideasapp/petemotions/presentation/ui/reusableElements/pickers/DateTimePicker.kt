package com.ideasapp.petemotions.presentation.ui.reusableElements.pickers

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.TIMETABLE_LOG_TAG
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import java.time.LocalDate
import java.time.ZoneId


@Composable
fun DateTimePicker(
    dateTime: Long,
    onValueChange: (Long) -> Unit
) {
    //from millis to LocalDateTime by Instant
    val initialDateTime = remember(dateTime) {
        val instant = java.time.Instant.ofEpochMilli(dateTime)
        val zoneId = java.time.ZoneId.systemDefault()
        java.time.LocalDateTime.ofInstant(instant, zoneId)
    }

    var selectedDate by remember { mutableStateOf(initialDateTime.toLocalDate()) }
    var selectedHour by remember { mutableIntStateOf(initialDateTime.hour) }
    var selectedMinute by remember { mutableIntStateOf(initialDateTime.minute) }
    val haptic = LocalHapticFeedback.current
    //convert selected date and time to milliseconds
    val dateTimeInMillis = remember(selectedDate, selectedHour, selectedMinute) {
        selectedDate
            .atTime(selectedHour, selectedMinute)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
    Log.d(TIMETABLE_LOG_TAG, "dateTime in millis: $dateTimeInMillis")

    LaunchedEffect(dateTimeInMillis) {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        onValueChange(dateTimeInMillis)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${"%02d".format(selectedDate.dayOfMonth)}.${"%02d".format(selectedDate.monthValue)}.${selectedDate.year}, " +
        "${"%02d".format(selectedHour)}:${"%02d".format(selectedMinute)}",
            style = MaterialTheme.typography.headlineSmall,
            color = MainTheme.colors.singleTheme,
            modifier = Modifier.padding(16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DatePicker(
                initialDate = LocalDate.now(),
                onDateChange = { newDate -> selectedDate = newDate },
                modifier = Modifier.weight(1f)
            )
            HourPicker(
                initial = selectedHour,
                onTimeChange = { hour ->
                    selectedHour = hour
                },
                modifier = Modifier.weight(0.5f)
            )
            MinutePicker(
                initial = selectedMinute,
                onTimeChange = { minute ->
                    selectedMinute = minute
                },
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}