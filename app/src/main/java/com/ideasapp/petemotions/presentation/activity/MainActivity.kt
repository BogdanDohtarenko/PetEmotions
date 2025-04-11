package com.ideasapp.petemotions.presentation.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ideasapp.petemotions.presentation.ui.screens.screen_containers.MainScreen
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.util.GlobalPreferences
import com.ideasapp.petemotions.presentation.util.workManager.DailyWorker
import com.ideasapp.petemotions.presentation.viewModels.CalendarViewModel
import com.ideasapp.petemotions.presentation.viewModels.DayAttributesViewModel
import com.ideasapp.petemotions.presentation.viewModels.StatisticsViewModel
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

//TODO (important)
// 8. top bar (filters)
// 31. profile screen
// 42. REFACTOR CalendarViewModel when it ready + by sumin flow
// 45. clean all files
// 49. reform ui directories (by sumin)
// 69. tips on how to manage day attributes
// 79. diagram for attributes
// 81. notifications for timetable
// 86. stub showing while load data

//TODO (design)
// 9. color scheme for light theme
// 27. icons for nav bar
// 55. russian language !!!
// 56. change spare color
// 82. adjust mood icons size
// 83. change attribute/statistics icons
// 88. animation
// 89. bottom sheet

//TODO (for future)
// 2. notifications with timetable
// 3. PERSONAL TIPS !
// 26. achievements
// 28. switch between screens by swipe
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
        grantPermissions()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun grantPermissions() {

            val permissionStatus = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            } else {
                Log.d("AutoFill", "permission granted")
                if (GlobalPreferences.isFirstLaunch(this)) {
                    Log.d("AutoFill", "isFirstLaunch true")
                    scheduleDailyWork()
                    GlobalPreferences.updateFirstLaunch(this)
                }
            }
    }

    private fun scheduleDailyWork() {
        Log.d("AutoFill", "Scheduling daily work")
        val constraints =
            Constraints.Builder()
                .build()

        val workRequest =
            PeriodicWorkRequest
                .Builder(DailyWorker::class.java, 24L, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }



    companion object {
        private val Context.dataStore by preferencesDataStore(name = "global_preferences")
        private val FIRST_LAUNCH_KEY = booleanPreferencesKey("first_launch")

        const val CALENDAR_LOG_TAG = "Calendar"
        const val TIMETABLE_LOG_TAG = "Timetable"
        const val STATISTICS_LOG_TAG = "Statistics"
    }
}

