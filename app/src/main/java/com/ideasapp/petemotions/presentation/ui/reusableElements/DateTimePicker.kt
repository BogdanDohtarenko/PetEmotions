package com.ideasapp.petemotions.presentation.ui.reusableElements

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
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.TIMETABLE_LOG_TAG
import com.ideasapp.petemotions.presentation.ui.screens.timetable.DatePicker
import com.ideasapp.petemotions.presentation.ui.screens.timetable.HourPicker
import com.ideasapp.petemotions.presentation.ui.screens.timetable.MinutePicker
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import java.time.LocalDate
import java.time.ZoneId


@Composable
fun DateTimePicker(
    onValueChange: (Long) -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedHour by remember { mutableIntStateOf(12) }
    var selectedMinute by remember { mutableIntStateOf(30) }

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
        onValueChange(dateTimeInMillis)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$selectedDate, $selectedHour:$selectedMinute",
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