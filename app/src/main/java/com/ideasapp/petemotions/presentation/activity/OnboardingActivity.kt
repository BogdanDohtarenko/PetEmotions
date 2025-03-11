package com.ideasapp.petemotions.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ideasapp.petemotions.presentation.ui.theme.PetEmotionsTheme
import dagger.hilt.android.AndroidEntryPoint


//TODO
// 1. Introduce user into app
// 2. Create pets, add pets creation to profile card
// 3. Save personal info about pets, save this all to sharedPref
//TODO (for long time)
// 1. loading with points (in tg channel)
@AndroidEntryPoint
class OnboardingActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetEmotionsTheme {
            }
        }
    }

}