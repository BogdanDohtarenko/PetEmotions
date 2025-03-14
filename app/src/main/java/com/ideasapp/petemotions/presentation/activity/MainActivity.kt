package com.ideasapp.petemotions.presentation.activity

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.ideasapp.petemotions.R
import com.ideasapp.petemotions.presentation.ui.screens.screen_containers.MainScreen
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.viewModels.CalendarViewModel
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel
import dagger.hilt.android.AndroidEntryPoint

//TODO
// 2.notifications with timetable !
// 3.PERSONAL TIPS !
// 4.moods on calendar !
// 5.normal day notes design !
// 6.autoFilling of days
// 8. top bar (filters, ...)
// 9. color scheme for light theme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //hilt provided viewModels
    private val calendarViewModel: CalendarViewModel by viewModels()
    private val timetableViewModel: TimetableViewModel by viewModels()

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        val isDarkTheme =
            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = if (isDarkTheme) Color.BLACK else Color.WHITE,
                darkScrim = if (isDarkTheme) Color.BLACK else Color.WHITE
            )
        )
        setContent {
            MainTheme {
                MainScreen(calendarViewModel, timetableViewModel)
            }
        }
    }

    companion object {
        const val CALENDAR_LOG_TAG = "Calendar"
        const val TIMETABLE_LOG_TAG = "Timetable"
    }
}

