package com.ideasapp.petemotions.presentation.activity

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ideasapp.petemotions.presentation.ui.screens.screen_containers.MainScreen
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.util.workManager.DailyWorker
import com.ideasapp.petemotions.presentation.viewModels.CalendarViewModel
import com.ideasapp.petemotions.presentation.viewModels.DayAttributesViewModel
import com.ideasapp.petemotions.presentation.viewModels.StatisticsViewModel
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

//TODO (important)
// 6. autoFilling of days !!!!
// 8. top bar (filters)
// 28. switch between screens by swipe
// 31. profile screen
// 42. REFACTOR CalendarViewModel when it ready + by sumin flow
// 45. clean all files
// 49. reform ui directories (by sumin)
// 69. tips on how to manage day attributes
// 72. edit attributes
// 76. day attributes list add to day item info !!!!
// 79. diagram for attributes
// 80. add permission handling

//TODO (design)
// 9. color scheme for light theme
// 27. icons for nav bar
// 55. russian language !!!
// 56. change spare color
// 82. adjust mood icons size
// 83. change attribute/statistics items

//TODO (for future)
// 2. notifications with timetable
// 3. PERSONAL TIPS !
// 26. achievements
// 33. happy birthday to every pet
// 60. migrate to ksp !!!
// 61. Huge amount of plots
// 64. deleting by swipe
// 73. by long click on date you can see attributes
// 75. my server + Workers to work with it
// 81. turn on/off auto filling

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //hilt provided viewModels
    private val calendarViewModel: CalendarViewModel by viewModels()
    private val timetableViewModel: TimetableViewModel by viewModels()
    private val attributesViewModel: DayAttributesViewModel by viewModels()
    private val statisticsViewModel:StatisticsViewModel by viewModels()

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
                MainScreen(
                    calendarViewModel,
                    timetableViewModel,
                    attributesViewModel,
                    statisticsViewModel
                )
            }
        }
        scheduleDailyWork()
    }

    //TODO make it out
    /*private fun scheduleDailyWork() {
        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(1, TimeUnit.DAYS)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailyWork",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )
    }*/
    private fun scheduleDailyWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(false)
            .build()

        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyWorker>(1, TimeUnit.MINUTES)
            .setInitialDelay(0, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        Log.d("AutoFill", "Scheduling daily work")
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailyWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            dailyWorkRequest
        )
    }

    companion object {
        const val CALENDAR_LOG_TAG = "Calendar"
        const val TIMETABLE_LOG_TAG = "Timetable"
        const val STATISTICS_LOG_TAG = "Statistics"
    }
}

