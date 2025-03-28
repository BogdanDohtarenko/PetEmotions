package com.ideasapp.petemotions.presentation.ui.screens.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion

@Composable
fun MoodPortionPlot(
    moodPortion: MoodPortion,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(20.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("G")
                Text("${moodPortion.badPercents}%")
            }
            Column(modifier = Modifier.padding(20.dp)) {
                Text("N")
                Text("${moodPortion.normalPercents}%")
            }
            Column(modifier = Modifier.padding(20.dp)) {
                Text("B")
                Text("${moodPortion.goodPercents}%")
            }
        }
    }
}