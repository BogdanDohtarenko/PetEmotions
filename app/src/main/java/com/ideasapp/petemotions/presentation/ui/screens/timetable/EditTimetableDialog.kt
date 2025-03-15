package com.ideasapp.petemotions.presentation.ui.screens.timetable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.ideasapp.petemotions.presentation.ui.reusableElements.DateTimePicker
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
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
        title = { Text(text = if (item == null) "Add" else "Edit") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                TextField(
                    value = description,
                    onValueChange = {description = it},
                    label = {Text("Description")},
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MainTheme.colors.mainColor,
                        unfocusedTextColor = MainTheme.colors.singleTheme,
                        focusedContainerColor = MainTheme.colors.mainColor,
                        focusedTextColor = MainTheme.colors.singleTheme,
                        cursorColor = MainTheme.colors.singleTheme,
                        focusedIndicatorColor = MainTheme.colors.singleTheme,
                        unfocusedIndicatorColor = MainTheme.colors.singleTheme.copy(alpha = 0.5f),
                        focusedLabelColor = MainTheme.colors.singleTheme,
                        unfocusedLabelColor = MainTheme.colors.singleTheme.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer( modifier = Modifier.height(8.dp) )
                DateTimePicker { dateTimeInMillis -> dateTime = dateTimeInMillis}
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
                },
                colors = ButtonColors(
                    containerColor = MainTheme.colors.buttonColor,
                    contentColor = MainTheme.colors.oppositeTheme,
                    disabledContentColor = MainTheme.colors.oppositeTheme,
                    disabledContainerColor = MainTheme.colors.buttonColor.copy(alpha = 0.5f)
                    )
            )  {
                Text(
                    "Save",
                    modifier = Modifier.background(MainTheme.colors.buttonColor)
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonColors(
                    containerColor = MainTheme.colors.buttonColor,
                    contentColor = MainTheme.colors.oppositeTheme,
                    disabledContentColor = MainTheme.colors.oppositeTheme,
                    disabledContainerColor = MainTheme.colors.buttonColor.copy(alpha = 0.5f)
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = MainTheme.colors.mainColor,
    )
}
