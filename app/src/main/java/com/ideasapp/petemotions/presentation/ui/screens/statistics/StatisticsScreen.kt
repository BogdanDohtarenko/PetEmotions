package com.ideasapp.petemotions.presentation.ui.screens.statistics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.presentation.ui.reusableElements.FoldableBox

@Composable
fun StatisticsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            FoldableBox(
                titleText = "plot",
                isExpandedByDefault = true
            ) {
                MoodPortionPlot(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            FoldableBox(
                titleText = "plot"
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("plot")
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            FoldableBox(
                titleText = "plot"
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("plot")
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            FoldableBox(
                titleText = "plot"
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("plot")
                }
            }
        }
    }
}