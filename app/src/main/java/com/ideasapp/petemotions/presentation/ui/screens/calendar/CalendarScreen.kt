package com.ideasapp.petemotions.presentation.ui.screens.calendar

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.util.DateUtil
import java.time.YearMonth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//TODO
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun CalendarScreen(
    uiState: CalendarUiState,
    onPreviousMonthButtonClicked: (prevMonth: YearMonth) -> Unit,
    onNextMonthButtonClicked: (nextMonth: YearMonth) -> Unit,
    onSaveDayInfoClick: (CalendarUiState.Date) -> Unit
) {
    var showDayInfoEditWindow by remember { mutableStateOf(false) }
    var dateClicked by remember { mutableStateOf<CalendarUiState.Date>(CalendarUiState.Date.Empty) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TopButtonCalendarBar()
            CalendarWidget(
                days = DateUtil.daysOfWeek,
                yearMonth = uiState.yearMonth,
                dates = uiState.dates,
                onPreviousMonthButtonClicked = onPreviousMonthButtonClicked,
                onNextMonthButtonClicked = onNextMonthButtonClicked,
                onDateClickListener = { dayClicked ->
                    Log.d("Calendar", "Day clicked: $dayClicked")
                    showDayInfoEditWindow = true
                    dateClicked = dayClicked
                }
            )
        }

        if (showDayInfoEditWindow) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        // Ничего не делаем, чтобы блокировать клики
                    }
            )
        }


        AnimatedVisibility(
            visible = showDayInfoEditWindow,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 500)
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 500)
            ) + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                DayInfoEdit(
                    onSaveDayInfoClick = onSaveDayInfoClick,
                    exitCallback = { showDayInfoEditWindow = false },
                    date = dateClicked
                )
            }
        }
    }
}





