package com.ideasapp.petemotions.presentation.ui.screens.timetable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem


@Composable
fun EditTimetableDialog(
    item: TimetableItem?,
    onDismiss: () -> Unit,
    onSave: (TimetableItem) -> Unit
) {
    var description by remember { mutableStateOf(item?.description ?: "") }
    var dateTime by remember { mutableStateOf(item?.dateTime ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (item == null) "Add" else "Edit") },
        text = {
            Column {
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = dateTime,
                    onValueChange = { dateTime = it },
                    label = { Text("Date and time") }, //TODO change format
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newItem = if(item != null) {
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
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}