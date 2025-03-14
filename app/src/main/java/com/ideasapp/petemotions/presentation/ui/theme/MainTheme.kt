package com.ideasapp.petemotions.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme.colors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) baseDarkPalette else baseLightPalette

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !darkTheme
        )
        systemUiController.setNavigationBarColor(
            color = if (darkTheme) baseDarkPalette.navigationBarColor else baseLightPalette.navigationBarColor,
            darkIcons = !darkTheme
        )
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        content = content
    )
}

object MainTheme {
    val colors: ColorPalette
        @Composable @ReadOnlyComposable
        get() = LocalColors.current
}
