package com.ideasapp.petemotions.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colors = if (!darkTheme) baseLightPalette
    else baseDarkPalette

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