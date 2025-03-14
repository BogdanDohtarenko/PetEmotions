package com.ideasapp.petemotions.presentation.ui.screens.timetable

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.TIMETABLE_LOG_TAG
import java.time.LocalDate
import java.time.ZoneId


@Composable
fun EditTimetableDialog(
    item: TimetableItem?,
    onDismiss: () -> Unit,
    onSave: (TimetableItem) -> Unit
) {
    var description by remember {mutableStateOf(item?.description ?: "")}
    var dateTime by remember {mutableLongStateOf(item?.dateTime ?: System.currentTimeMillis())}

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text(text = if (item == null) "Add" else "Edit")},
        text = {
            Column {
                TextField(
                    value = description, onValueChange = {description = it},
                    label = {Text("Description")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                DateTimePickerExample { dateTimeInMillis -> dateTime = dateTimeInMillis}
            }
        },
        confirmButton = {
        Button(onClick = {
            val newItem = if (item != null) {
                TimetableItem(
                    description = description,
                    dateTime = dateTime,
                    id = item.id
                )
            } else {
                TimetableItem(
                    description = description,
                    dateTime = dateTime,
                )
            }
            onSave(newItem)
            })  {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DateTimePickerExample(
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
            TimePicker(
                initialHour = selectedHour,
                initialMinute = selectedMinute,
                onTimeChange = { hour, minute ->
                    selectedHour = hour
                    selectedMinute = minute
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}