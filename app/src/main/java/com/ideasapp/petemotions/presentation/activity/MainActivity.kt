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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ideasapp.petemotions.presentation.ui.screens.onboarding.GreetingsScreen
import com.ideasapp.petemotions.presentation.ui.screens.screen_containers.MainScreen
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.util.GlobalPreferences
import com.ideasapp.petemotions.presentation.util.services.DailyWorker
import com.ideasapp.petemotions.presentation.util.services.requestBootPermission
import com.ideasapp.petemotions.presentation.util.services.requestExactAlarmPermission
import com.ideasapp.petemotions.presentation.viewModels.CalendarViewModel
import com.ideasapp.petemotions.presentation.viewModels.DayAttributesViewModel
import com.ideasapp.petemotions.presentation.viewModels.StatisticsViewModel
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

//TODO (important)
// 8. top bar (filters)
// 42. REFACTOR CalendarViewModel when it ready + by sumin flow
// 45. clean all files
// 96. onBoarding

//TODO (design)
// 55. russian language !!!
// 86. stub showing while load data
// 88. animations
// 97. onBoarding with good animations

//TODO (for future)
// 3. PERSONAL TIPS !
// 26. achievements
// 28. switch between screens by swipe
// 33. happy birthday to every pet
// 60. migrate to ksp !!!
// 61. Huge amount of plots
// 64. deleting by swipe
// 69. tips on how to manage day attributes
// 73. by long click on date you can see attributes
// 75. my server + Workers to work with it
// 81. turn on/off auto filling
// 90. deeplink
// 91. set reminder status done
// 95. tips on how to manage timetable

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
            val isOnboarding = remember {mutableStateOf(GlobalPreferences.onBoardingLaunch(this))}
            if (isOnboarding.value) {
                MainTheme {
                    GreetingsScreen(calendarViewModel,onExitClick = {
                        GlobalPreferences.updateOnBoardingLaunch(this)
                        isOnboarding.value = false
                    })
                }
            } else {
                MainTheme {
                    MainScreen(calendarViewModel,timetableViewModel,attributesViewModel,statisticsViewModel)
                }
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
            Log.d("AutoFill", "Notification permission granted")
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
        const val CALENDAR_LOG_TAG = "Calendar"
        const val TIMETABLE_LOG_TAG = "Timetable"
        const val STATISTICS_LOG_TAG = "Statistics"
    }
}

