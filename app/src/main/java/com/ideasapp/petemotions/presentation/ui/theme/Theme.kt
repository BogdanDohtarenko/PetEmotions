package com.ideasapp.petemotions.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = green,
    secondary = Color(0xFFFFD700),
    tertiary = Color(0xFF005F73),
    onBackground = Color(0xFFFFD700)
)

private val LightColorScheme = lightColorScheme(
    primary = green,
    secondary = Color(0xFFFFD700),
    tertiary = Color(0xFFF5F5F5),
    onBackground = Color(0xFFFFD700)
)

@Composable
fun PetEmotionsTheme(darkTheme:Boolean=isSystemInDarkTheme(), // Dynamic color is available on Android 12+
                     dynamicColor:Boolean=true,content:@Composable ()->Unit) {
    val colorScheme=when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S-> {
            val context=LocalContext.current
            if(darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme->DarkColorScheme
        else->LightColorScheme
    }

    MaterialTheme(colorScheme=colorScheme,typography=Typography,content=content)
}