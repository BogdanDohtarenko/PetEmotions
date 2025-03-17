package com.ideasapp.petemotions.presentation.ui.screens.timetable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.presentation.ui.reusableElements.pickers.DateTimePicker
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun EditTimetableDialog(
    item: TimetableItem?,
    onDismiss: () -> Unit,
    onSave: (TimetableItem) -> Unit
) {
    // without by to avoid unnecessary recompositions
    val description = remember {mutableStateOf(item?.description ?: "")}
    val dateTime = remember {mutableLongStateOf(item?.dateTime ?: System.currentTimeMillis())}

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            AddEditTitle(item)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                CustomTextField(description)
                Spacer(modifier = Modifier.height(8.dp))
                DateTimePicker(dateTime.longValue) { dateTimeInMillis -> dateTime.longValue = dateTimeInMillis}
            }
        },
        confirmButton = {
            CanselSaveButtons(onDismiss, item, description.value, dateTime.longValue, onSave)
        },
        containerColor = MainTheme.colors.mainColor,
    )
}

@Composable
private fun AddEditTitle(item : TimetableItem?) {
    Text(text = if (item == null) "Add" else "Edit", textAlign = TextAlign.Center, modifier = Modifier)
}

@Composable
fun CustomTextField(description: MutableState<String>) {
    TextField(
        value = description.value,
        onValueChange = { description.value = it },
        label = { Text("Description") },
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
}

@Composable
private fun CanselSaveButtons(
    onDismiss : ()->Unit,
    item : TimetableItem?,
    description : String,
    dateTime : Long,
    onSave : (TimetableItem)->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(
                containerColor = MainTheme.colors.buttonColor,
                contentColor = MainTheme.colors.oppositeTheme,
                disabledContentColor = MainTheme.colors.oppositeTheme,
                disabledContainerColor = MainTheme.colors.buttonColor.copy(alpha = 0.5f)
            )
        ) {
            Text("Cancel")
        }
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
        }, colors = ButtonDefaults.buttonColors(containerColor = MainTheme.colors.buttonColor, contentColor = MainTheme.colors.oppositeTheme, disabledContentColor = MainTheme.colors.oppositeTheme, disabledContainerColor = MainTheme.colors.buttonColor.copy(alpha = 0.5f))) {
            Text("Save")
        }
    }
}
