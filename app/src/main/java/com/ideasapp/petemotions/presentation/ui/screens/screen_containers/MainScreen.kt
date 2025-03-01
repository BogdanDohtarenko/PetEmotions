package com.ideasapp.petemotions.presentation.ui.screens.screen_containers

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ideasapp.petemotions.presentation.ui.reusableElements.BottomNavigationBar
import com.ideasapp.petemotions.presentation.ui.reusableElements.NavigationHost
import com.ideasapp.petemotions.presentation.ui.screens.calendar.CalendarScreen
import com.ideasapp.petemotions.presentation.viewModels.CalendarViewModel
import androidx.compose.runtime.getValue
import com.ideasapp.petemotions.presentation.navigation.NavItem
import com.ideasapp.petemotions.presentation.ui.screens.calendar.DayInfoEdit
import com.ideasapp.petemotions.presentation.util.toJson
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainScreen(
    viewModel: CalendarViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    Scaffold(
        //set bottom nav bar
        bottomBar = { BottomNavigationBar(navController) },
        //to arrange above system buttons
        modifier = Modifier.navigationBarsPadding(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            //Set nav graph
            NavigationHost(
                navController = navController,
                statisticsScreenContent = { Text("statistics")},
                calendarScreenContent = {
                    CalendarScreen(
                        uiState = uiState,
                        onPreviousMonthButtonClicked = { prevMonth ->
                            viewModel.toPreviousMonth(prevMonth)
                        },
                        onNextMonthButtonClicked = { nextMonth ->
                            viewModel.toNextMonth(nextMonth)
                        },
                        onEditDayClick = { date ->
                            //cast CalendarUiState.Date to string
                            val dateJson = date.toJson()
                            //Navigate to EditDay
                            navController.navigate("${NavItem.EditDay.route}/${dateJson}")
                        }
                    )},
                dayInfoEditContent = { date, onClose ->
                    //invoke day edit
                    DayInfoEdit(
                        dateItem = date,
                        onSaveDayInfoClick = { newDay ->
                            viewModel.addOrEditDayItem(newDay.dayInfoItem)
                        },
                        exitCallback = onClose
                    )
                },
                timetableScreenContent = { Text("timetable")}
            )
        }
    }
}