package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.AddEditTitle
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.AttributeCanselSaveButtons
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.CustomTextField
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun AddEditAttributeDialog(
    itemState: MutableState<DayAttribute?>,
    attributeBoxType: String,
    onDismiss: () -> Unit,
    onSave: (DayAttribute) -> Unit
) {
    val item = remember { itemState.value }
    // without by to avoid unnecessary recompositions
    val title = remember {mutableStateOf(item?.title ?: "")}
    val imageVector = remember {mutableStateOf(item?.imageVector ?: Icons.Default.FavoriteBorder) }

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
                CustomTextField(title)
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        confirmButton = {
            AttributeCanselSaveButtons(
                onDismiss = onDismiss,
                item = item,
                attributeBoxType = attributeBoxType,
                title = title.value,
                imageVector = imageVector.value,
                onSave = onSave,
            )
        },
        containerColor = MainTheme.colors.mainColor,
    )
}