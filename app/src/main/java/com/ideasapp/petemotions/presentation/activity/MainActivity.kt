package com.ideasapp.petemotions.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ideasapp.petemotions.presentation.ui.screens.calendar.CalendarScreen
import com.ideasapp.petemotions.presentation.ui.screens.screen_containers.MainScreen
import com.ideasapp.petemotions.presentation.ui.theme.PetEmotionsTheme
import com.ideasapp.petemotions.presentation.viewModels.CalendarViewModel
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel
import dagger.hilt.android.AndroidEntryPoint

//TODO
// 1.custom theme !
// 2.notifications with timetable !
// 3.PERSONAL TIPS !
// 4.moods on calendar !
// 5.normal day notes design !
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //hilt provided viewModels
    private val calendarViewModel: CalendarViewModel by viewModels()

    private val timetableViewModel: TimetableViewModel by viewModels()

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetEmotionsTheme {
                MainScreen(calendarViewModel, timetableViewModel)
            }
        }
    }

    companion object {
        const val CALENDAR_LOG_TAG = "Calendar"
        const val TIMETABLE_LOG_TAG = "Timetable"
    }
}

