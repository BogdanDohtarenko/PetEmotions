package com.ideasapp.petemotions.presentation.activity

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ideasapp.petemotions.presentation.ui.screens.screen_containers.MainScreen
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.viewModels.CalendarViewModel
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel
import dagger.hilt.android.AndroidEntryPoint

//TODO (important)
// 6. autoFilling of days !
// 8. top bar (filters)
// 12. mood plot  !!
// 28. switch between screens by swipe
// 31. profile screen
// 42. REFACTOR CalendarViewModel when it ready + by sumin flow
// 45. clean all files
// 49. reform ui directories (by sumin)
// 50. add custom day info attributes
// 51. pass dynamic quantity of day info attributes
// 52. make day info edit all clickable (add map) !!!
// 58. data store pref for attributes
// 60. migrate to ksp !!!
// 66. day edit info divide into different files
// 68. add edit attribute screen
// 69.

//TODO (design)
// 4. moods on calendar !
// 5. normal day notes design !!
// 9. color scheme for light theme
// 24. vibration while scrolling dates in picker
// 27. icons for nav bar
// 55. russian language !!!
// 56. change spare color
// 59. draw all necessary icons !!!
// 63. background color in dark theme -> dark gray
// 67. nav bar design

//TODO (for future)
// 2. notifications with timetable
// 3. PERSONAL TIPS !
// 26. achievements
// 33. happy birthday to every pet
// 61. Huge amount of plots
// 64. deleting by swipe

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

        const val MOOD_STATE_GOOD = 3
        const val MOOD_STATE_NORMAL = 2
        const val MOOD_STATE_BAD = 1
    }
}

