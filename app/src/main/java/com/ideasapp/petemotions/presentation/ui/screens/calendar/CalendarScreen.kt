package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.util.DateUtil
import java.time.YearMonth

//TODO
//TODO store data outside to prevent long loading
@Composable
fun CalendarScreen(
    uiState: CalendarUiState,
    onPreviousMonthButtonClicked: (prevMonth: YearMonth) -> Unit,
    onNextMonthButtonClicked: (nextMonth: YearMonth) -> Unit,
    onDateClickListener: (CalendarUiState.Date) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) { //TODO delete viewModel
        TopButtonCalendarBar()
        //MonthDropDownMenu() //TODO Delete
        //WeekdaysRow() //TODO Delete
        CalendarWidget(
            days = DateUtil.daysOfWeek,
            yearMonth = uiState.yearMonth,
            dates = uiState.dates,
            onPreviousMonthButtonClicked = onPreviousMonthButtonClicked,
            onNextMonthButtonClicked = onNextMonthButtonClicked,
            onDateClickListener = onDateClickListener
        )
    }
}


//TODO throw all this functions to different directories



//Class to get all dates and to set empty previous month
class CalendarDataSource {

}





