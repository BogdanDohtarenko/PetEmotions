package com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun AttributeCanselSaveButtons(
    onDismiss : ()->Unit,
    item : DayAttribute?,
    title : String,
    imageVector : ImageVector,
    onSave : (DayAttribute) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = MainTheme.colors.buttonColor, contentColor = MainTheme.colors.oppositeTheme, disabledContentColor = MainTheme.colors.oppositeTheme, disabledContainerColor = MainTheme.colors.buttonColor.copy(alpha = 0.5f))) {
            Text("Cancel")
        }
        Button(onClick = {
            val newItem = if (item != null) {
                DayAttribute(
                    imageVector = imageVector,
                    title = title,
                    id = item.id
                )
            } else {
                DayAttribute(
                    imageVector = imageVector,
                    title = title,
                    )
            }
            onSave(newItem)
        }, colors = ButtonDefaults.buttonColors(containerColor = MainTheme.colors.buttonColor, contentColor = MainTheme.colors.oppositeTheme, disabledContentColor = MainTheme.colors.oppositeTheme, disabledContainerColor = MainTheme.colors.buttonColor.copy(alpha = 0.5f))) {
            Text("Save")
        }
    }
}