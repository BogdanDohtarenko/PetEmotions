package com.ideasapp.petemotions.presentation.ui.screens.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.CALENDAR_LOG_TAG

@Composable
fun DayInfoEdit(
    onSaveDayInfoClick: (CalendarUiState.Date) -> Unit,
    exitCallback: () -> Unit,
    date: CalendarUiState.Date?
) {
    if (date == null) {
        Log.d(CALENDAR_LOG_TAG, "choose date == null")
    }
    Box(modifier = Modifier.background(Color.White).fillMaxSize()) {
        Text("Edit day $date", color = Color.Black)
    }
}
//TODO