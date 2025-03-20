package com.ideasapp.petemotions.presentation.ui.screens.screen_containers

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ideasapp.petemotions.presentation.navigation.NavItem
import com.ideasapp.petemotions.presentation.ui.reusableElements.BottomNavigationBar
import com.ideasapp.petemotions.presentation.ui.reusableElements.NavigationHost
import com.ideasapp.petemotions.presentation.ui.reusableElements.showToast
import com.ideasapp.petemotions.presentation.ui.screens.calendar.CalendarScreen
import com.ideasapp.petemotions.presentation.ui.screens.calendar.DayInfoEdit
import com.ideasapp.petemotions.presentation.ui.screens.statistics.StatisticsScreen
import com.ideasapp.petemotions.presentation.ui.screens.timetable.FullTimetableScreen
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.util.toJson
import com.ideasapp.petemotions.presentation.viewModels.CalendarViewModel
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun MainScreen(
    calendarViewModel: CalendarViewModel,
    timetableViewModel: TimetableViewModel,
) {
    val petIdGlobal = remember { mutableIntStateOf(0) }
    val petsList by calendarViewModel.petsList.collectAsState(initial = emptyList())
    val timetableFlow = timetableViewModel.timetableFlow
    val uiState by calendarViewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val context = LocalContext.current as ComponentActivity
    context.enableEdgeToEdge()


    Scaffold(
        //set bottom nav bar
        bottomBar = { BottomNavigationBar(navController) },
        //to arrange above system buttons
        modifier = Modifier
            .navigationBarsPadding()
            .background(MainTheme.colors.singleTheme)
            .systemBarsPadding(),

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(MainTheme.colors.singleTheme),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            //Set nav graph
            NavigationHost(
                navController = navController,
                statisticsScreenContent = {
                    StatisticsScreen()
                },
                calendarScreenContent = {
                    CalendarScreen(
                        uiState = uiState,
                        petsList = petsList,
                        petId = petIdGlobal,
                        onPetClick = { petId ->
                            petIdGlobal.intValue = petId
                            calendarViewModel.onChangePet(petId)
                        },
                        onPreviousMonthButtonClicked = { prevMonth ->
                            calendarViewModel.toPreviousMonth(prevMonth)
                        },
                        onNextMonthButtonClicked = { nextMonth ->
                            calendarViewModel.toNextMonth(nextMonth)
                        },
                        onEditDayClick = { date,  petId ->
                            //cast CalendarUiState.Date to string
                            val dateJson = date.toJson()
                            //Navigate to EditDay
                            navController.navigate("${NavItem.EditDay.route}/${dateJson}/${petId}")
                        },
                    )},
                dayInfoEditContent = { date, onClose, petId ->
                    DayInfoEdit(
                        dateItem = date,
                        onSaveDayInfoClick = { newDayInfo ->
                            calendarViewModel.addOrEditDayItem(newDayInfo)
                            showToast(context, "day saved")
                        },
                        exitCallback = onClose,
                        petId = petId,
                        optionalAttributesFood = calendarViewModel.getDayAttributesFood(),
                        optionalAttributesEvents = calendarViewModel.getDayAttributesEvents(),
                        optionalAttributesHealth = calendarViewModel.getDayAttributesHealth(),
                    )
                },
                timetableScreenContent = {
                    FullTimetableScreen(
                        timetableFlow = timetableFlow,
                        onAddTimetableItem = { item ->
                            timetableViewModel.addItem(item)
                            showToast(context, "item added")
                        },
                        onDeleteTimetableItem = { item ->
                            timetableViewModel.deleteItem(item)
                            showToast(context, "item deleted")
                        }
                    )
                }
            )
        }
    }
}