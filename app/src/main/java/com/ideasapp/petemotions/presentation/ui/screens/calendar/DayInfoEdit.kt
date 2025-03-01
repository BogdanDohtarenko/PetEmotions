package com.ideasapp.petemotions.presentation.ui.screens.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.CALENDAR_LOG_TAG
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DayInfoEdit(
    onSaveDayInfoClick: (CalendarUiState.Date) -> Unit,
    exitCallback: () -> Unit,
    dateItem: CalendarUiState.Date?
) {
    if (dateItem == null) throw RuntimeException("Date to DayInfoEdit = null")

    val textColor =  MaterialTheme.colorScheme.onBackground
    val dateLocalDate = LocalDate.ofEpochDay(dateItem.dayInfoItem.date)

    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(scrollState),
        ) {
            HeaderForDay(dateLocalDate, textColor, exitCallback)
            MoodChooseBox(textColor)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun MoodChooseBox(textColor : Color) {
    Box(modifier = Modifier.fillMaxWidth(0.7f).padding(vertical = 8.dp).background(Color.Gray)) {
        Row(modifier = Modifier.align(Alignment.Center)) {
            Text("B", color = textColor, modifier = Modifier.align(Alignment.CenterVertically).padding(20.dp, 3.dp))
            Text("N", color = textColor, modifier = Modifier.align(Alignment.CenterVertically).padding(20.dp, 3.dp))
            Text("G", color = textColor, modifier = Modifier.align(Alignment.CenterVertically).padding(20.dp, 3.dp))
        }
    }
}

@Composable
private fun HeaderForDay(dateLocalDate : LocalDate, textColor : Color,  exitCallback: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = exitCallback, modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back",
                tint = textColor
            )
        }
        Text(
            "${dateLocalDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())},"+" ${dateLocalDate.dayOfMonth}"+" ${dateLocalDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())}",
            color = textColor,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(8f).align(Alignment.CenterVertically).padding(80.dp, 10.dp)
        )
    }
}