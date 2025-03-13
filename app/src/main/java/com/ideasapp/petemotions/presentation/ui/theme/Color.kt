package com.ideasapp.petemotions.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80=Color(0xFFD0BCFF)
val PurpleGrey80=Color(0xFFCCC2DC)
val Pink80=Color(0xFFEFB8C8)

val Purple40=Color(0xFF6650a4)
val PurpleGrey40=Color(0xFF625b71)
val Pink40=Color(0xFF7D5260)

val baseLightPalette = ColorPalette(
    mainColor = Pink40,
    singleTheme = Color.White,
    oppositeTheme = Color.Black,
    buttonColor = Color(0xFFEFEEEE)
)
val baseDarkPalette = baseLightPalette.copy(
    mainColor = Pink80,
    singleTheme = Color.Black,
    oppositeTheme = Color.White,
    buttonColor = Color(0xFF2D2D31)
)
val LocalColors = staticCompositionLocalOf<ColorPalette> {
    error("Colors composition error")
}