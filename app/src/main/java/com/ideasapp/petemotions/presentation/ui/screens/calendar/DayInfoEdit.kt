package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState

@Composable
fun DayInfoEdit(
    onSaveDayInfoClick: (CalendarUiState.Date) -> Unit,
    exitCallback: () -> Unit,
    date: CalendarUiState.Date
) {
    Box(modifier = Modifier.background(Color.Gray).fillMaxSize()) {
        Text("Edit day $date")
    }
}
//TODO