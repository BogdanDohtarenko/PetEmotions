package com.ideasapp.petemotions.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ideasapp.petemotions.presentation.ui.screens.calendar.CalendarScreen
import com.ideasapp.petemotions.presentation.ui.screens.screen_containers.MainScreen
import com.ideasapp.petemotions.presentation.ui.theme.PetEmotionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetEmotionsTheme {
                MainScreen()
            }
        }
    }
}

