package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.presentation.ui.reusableElements.pickers.IconsPicker
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.AddEditTitle
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.AttributeCanselSaveButtons
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.CustomTextField
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun AddEditAttributeDialog(
    itemState: MutableState<DayAttribute?>,
    possibleIconsList: List<ImageVector>,
    attributeBoxType: String,
    onDismiss: () -> Unit,
    onSave: (DayAttribute) -> Unit
) {
    // without by to avoid unnecessary recompositions
    val title = remember { mutableStateOf(itemState.value?.title ?: "") }
    val imageVector = remember { mutableStateOf(itemState.value?.imageVector ?: Icons.Default.FavoriteBorder) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            AddEditTitle(itemState.value)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                //TODO constraint text size
                CustomTextField(
                    title
                )
                Spacer(modifier = Modifier.height(18.dp))
                IconsPicker(
                    iconsList = possibleIconsList,
                    onIconClick = { imageVector.value = it }
                )
                Spacer(modifier = Modifier.height(18.dp))
                AttributeCreatingPreview(
                    title = title.value,
                    imageVector = imageVector.value,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        },
        confirmButton = {
            AttributeCanselSaveButtons(
                onDismiss = onDismiss,
                item = DayAttribute(
                    title = title.value,
                    imageVector = imageVector.value,
                    type = attributeBoxType
                ),
                attributeBoxType = attributeBoxType,
                title = title.value,
                imageVector = imageVector.value,
                onSave = onSave,
            )
        },
        containerColor = MainTheme.colors.mainColor,
    )
}