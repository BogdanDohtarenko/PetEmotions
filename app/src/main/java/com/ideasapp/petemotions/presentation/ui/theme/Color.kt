package com.ideasapp.petemotions.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80=Color(0xFFD0BCFF)
val PurpleGrey80=Color(0xFFCCC2DC)
val Pink80=Color(0xFFEFB8C8)

val Purple40=Color(0xFF6650a4)
val PurpleGrey40=Color(0xFF625b71)
val Pink40=Color(0xFF7D5260)

val darkGray: Color = Color(0xFF191919)
val lightGray: Color = Color(0xFFbebcbb)
val orange: Color = Color(0xFFf7b843)
val yellow: Color = Color(0xFFfff263)
val green: Color = Color(0xFF47bf67)
val red: Color = Color(0xFFff5d0b)
val lightBrown: Color = Color(0xFFfcb875)
val darkBrown: Color = Color(0xFFb28151)

val baseLightPalette = ColorPalette(
    mainColor = yellow,
    singleTheme = lightGray,
    oppositeTheme = Color.Black,
    buttonColor = Color(0xFFEFEEEE),
    navigationBarColor = Color.White,
    statusBarColor = Color.Transparent,
    spareContentColor = lightBrown,
    warningModeColor = red,
)
val baseDarkPalette = baseLightPalette.copy(
    mainColor = orange,
    singleTheme = darkGray,
    oppositeTheme = Color.White,
    buttonColor = Color(0xFF2D2D31),
    navigationBarColor = Color.Black,
    statusBarColor = Color.Transparent,
    spareContentColor = darkBrown,
    warningModeColor = red,
)
val LocalColors = staticCompositionLocalOf<ColorPalette> {
    error("Colors composition error")
}
