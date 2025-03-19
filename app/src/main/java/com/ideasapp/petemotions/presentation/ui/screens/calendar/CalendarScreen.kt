package com.ideasapp.petemotions.presentation.ui.screens.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.util.CalendarDateUtil
import java.time.YearMonth

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun CalendarScreen(
    uiState: CalendarUiState,
    petId: MutableIntState,
    petsList: List<Pet>,
    onPreviousMonthButtonClicked: (prevMonth: YearMonth) -> Unit,
    onNextMonthButtonClicked: (nextMonth: YearMonth) -> Unit,
    onEditDayClick: (CalendarUiState.Date, Int) -> Unit,
    onPetClick: (Int) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MainTheme.colors.singleTheme)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(MainTheme.colors.singleTheme)
        ) {
            TopButtonCalendarBar(
                pets = petsList,
                onPetClick = onPetClick,
                petId = petId
            ) //TODO FILTER
            CalendarWidget(
                days = CalendarDateUtil.daysOfWeek,
                yearMonth = uiState.yearMonth,
                dates = uiState.dates,
                onPreviousMonthButtonClicked = onPreviousMonthButtonClicked,
                onNextMonthButtonClicked = onNextMonthButtonClicked,
                onDateClickListener = { dayClicked ->
                    onEditDayClick(dayClicked, petId.intValue)
                }
            )
        }
    }
}





