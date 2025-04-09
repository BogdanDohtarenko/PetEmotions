package com.ideasapp.petemotions.presentation.ui.screens.statistics

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ideasapp.petemotions.domain.entity.stastistics.ChartModel
import com.ideasapp.petemotions.presentation.ui.reusableElements.ChartCirclePie

@Composable
fun AttributesDiagram() {
    val charts = listOf(
        ChartModel(value = 20f, color = Color.Black),
        ChartModel(value = 30f, color = Color.Gray),
        ChartModel(value = 45f, color = Color.Green),
        ChartModel(value = 5f, color = Color.Red),
    )
    ChartCirclePie(
        modifier = Modifier,
        charts = charts
    )
}