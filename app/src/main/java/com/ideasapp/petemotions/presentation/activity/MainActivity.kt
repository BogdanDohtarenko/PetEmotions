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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //hilt provided viewModels
    private val calendarViewModel: CalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetEmotionsTheme {
                MainScreen(calendarViewModel)
            }
        }
    }

    companion object {
        const val CALENDAR_LOG_TAG = "Calendar"
    }
}

