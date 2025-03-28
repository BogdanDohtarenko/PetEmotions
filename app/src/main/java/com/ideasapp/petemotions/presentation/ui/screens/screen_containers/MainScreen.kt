package com.ideasapp.petemotions.presentation.ui.screens.screen_containers

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.CALENDAR_LOG_TAG
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
import com.ideasapp.petemotions.presentation.viewModels.DayAttributesViewModel
import com.ideasapp.petemotions.presentation.viewModels.StatisticsViewModel
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun MainScreen(
    calendarViewModel: CalendarViewModel,
    timetableViewModel: TimetableViewModel,
    attributesViewModel: DayAttributesViewModel,
    statisticsViewModel: StatisticsViewModel
) {
    val petIdCalendar = remember { mutableIntStateOf(0) }
    val petIdStatistics = remember { mutableIntStateOf(0) }
    val petIdTimetable = remember { mutableIntStateOf(0) } //TODO Add pets to timetable
    val petsList by calendarViewModel.petsList.collectAsState(initial = emptyList())
    val moodPortion = statisticsViewModel.moodPortion.observeAsState()
    val moodOfYear = statisticsViewModel.moodOfYear.observeAsState()
    val timetableFlow = timetableViewModel.timetableFlow
    val uiState by calendarViewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val context = LocalContext.current as ComponentActivity
    val haptic =  LocalHapticFeedback.current
    context.enableEdgeToEdge()


    Scaffold(
        //set bottom nav bar
        bottomBar = { BottomNavigationBar(navController) },
        //to arrange above system buttons
        modifier = Modifier
            .navigationBarsPadding()
            .background(MainTheme.colors.singleTheme)
            .systemBarsPadding(),
    ) {
        paddingValues ->
        val heightToDecrease = 20.dp //Custom shape's notch height to decrease from bottom padding
        Box(Modifier.fillMaxSize().padding(
            PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() - heightToDecrease))) {

        }
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
                    statisticsViewModel.getMoodOfYearByMonth(petIdStatistics.intValue)
                    statisticsViewModel.getMoodPortionData(petIdStatistics.intValue)
                    StatisticsScreen(
                        petsList = petsList,
                        petId = petIdStatistics,
                        onPetClick = { petId ->
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            petIdCalendar.intValue = petId
                            calendarViewModel.onChangePet(petId)
                        },
                        moodPortion = moodPortion.value,
                        moodOfYear = moodOfYear.value
                    )
                },
                calendarScreenContent = {
                    CalendarScreen(
                        uiState = uiState,
                        petsList = petsList,
                        petId = petIdCalendar,
                        onPetClick = { petId ->
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            petIdCalendar.intValue = petId
                            calendarViewModel.onChangePet(petId)
                        },
                        onPreviousMonthButtonClicked = { prevMonth ->
                            calendarViewModel.toPreviousMonth(prevMonth)
                        },
                        onNextMonthButtonClicked = { nextMonth ->
                            calendarViewModel.toNextMonth(nextMonth)
                        },
                        onEditDayClick = { date,  petId ->
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
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
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            calendarViewModel.addOrEditDayItem(newDayInfo)
                            showToast(context, "day saved")
                        },
                        exitCallback = onClose,
                        petId = petId,
                        possibleIconsList = calendarViewModel.getPossibleIcons(),
                        dayAttributesFlow = attributesViewModel.attributesList,
                        onAddAttributeClick = { dayAttribute ->
                            Log.d(CALENDAR_LOG_TAG, "trying add dayAttribute ${dayAttribute.title} with type ${dayAttribute.type}")
                            showToast(context, "${dayAttribute.title} added")
                            attributesViewModel.onAddDayAttribute(dayAttribute)
                        },
                        onDeleteAttributeClick = { dayAttribute ->
                            Log.d(CALENDAR_LOG_TAG, "trying to delete ${dayAttribute.title} with type ${dayAttribute.type}")
                            showToast(context, "${dayAttribute.title} deleted")
                            attributesViewModel.onDeleteDayAttribute(dayAttribute)
                        }
                    )
                },
                timetableScreenContent = {
                    FullTimetableScreen(
                        timetableFlow = timetableFlow,
                        onAddTimetableItem = { item ->
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            timetableViewModel.addItem(item)
                            showToast(context, "item added")
                        },
                        onDeleteTimetableItem = { item ->
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            timetableViewModel.deleteItem(item)
                            showToast(context, "item deleted")
                        }
                    )
                }
            )
        }
    }
}