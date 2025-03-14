package com.ideasapp.petemotions.presentation.ui.screens.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.util.CalendarDateUtil
import java.time.YearMonth
import androidx.compose.ui.Modifier
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

//TODO
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun CalendarScreen(
    uiState: CalendarUiState,
    onPreviousMonthButtonClicked: (prevMonth: YearMonth) -> Unit,
    onNextMonthButtonClicked: (nextMonth: YearMonth) -> Unit,
    onEditDayClick: (CalendarUiState.Date) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MainTheme.colors.singleTheme)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(MainTheme.colors.singleTheme)
        ) {
            TopButtonCalendarBar() //TODO FILTER
            CalendarWidget(
                days = CalendarDateUtil.daysOfWeek,
                yearMonth = uiState.yearMonth,
                dates = uiState.dates,
                onPreviousMonthButtonClicked = onPreviousMonthButtonClicked,
                onNextMonthButtonClicked = onNextMonthButtonClicked,
                onDateClickListener = { dayClicked ->
                    onEditDayClick(dayClicked)
                }
            )
        }
    }
}





