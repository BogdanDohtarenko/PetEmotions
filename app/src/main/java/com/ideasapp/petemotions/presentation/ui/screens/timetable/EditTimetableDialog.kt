package com.ideasapp.petemotions.presentation.ui.screens.timetable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.presentation.ui.reusableElements.pickers.DateTimePicker
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.AddEditTitle
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.TimetableCanselSaveButtons
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.CustomTextField
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
            TimetableCanselSaveButtons(onDismiss, item, description.value, dateTime.longValue, onSave)
        },
        containerColor = MainTheme.colors.mainColor,
    )
}



