package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.R
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.util.DateUtil
import com.ideasapp.petemotions.presentation.util.getDayOfMonthStartingFromMonday
import com.ideasapp.petemotions.presentation.util.getDisplayName
import com.ideasapp.petemotions.presentation.viewModels.CalendarViewModel
import java.time.LocalDate
import java.time.YearMonth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

//TODO
//TODO store data outside to prevent long loading
@Composable
fun CalendarScreen(viewModel: CalendarViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) { //TODO delete viewModel
        val uiState by viewModel.uiState.collectAsState()
        TopButtonCalendarBar()
        //MonthDropDownMenu() //TODO Delete
        //WeekdaysRow() //TODO Delete
        CalendarWidget(
            days = DateUtil.daysOfWeek,
            yearMonth = uiState.yearMonth,
            dates = uiState.dates,
            onPreviousMonthButtonClicked = { prevMonth ->
                viewModel.toPreviousMonth(prevMonth)
            },
            onNextMonthButtonClicked = { nextMonth ->
                viewModel.toNextMonth(nextMonth)
            },
            onDateClickListener = {
                // TODO("set on date click listener")
            }
        )
    }
}


//TODO throw all this functions to different directories



//Class to get all dates and to set empty previous month
class CalendarDataSource {

}





