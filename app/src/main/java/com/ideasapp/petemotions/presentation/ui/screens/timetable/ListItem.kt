package com.ideasapp.petemotions.presentation.ui.screens.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.util.millisToLocalDateAndTime
import com.ideasapp.petemotions.presentation.util.toDateTimeString

@Composable
fun ListItem(item: TimetableItem, onClick: () -> Unit, onLongClick: () -> Unit) {
    val localDateTime = millisToLocalDateAndTime(item.dateTime).toDateTimeString()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MainTheme.colors.mainColor, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {onClick()},
                    onLongPress = {onLongClick()},
                )
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = item.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = localDateTime,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
