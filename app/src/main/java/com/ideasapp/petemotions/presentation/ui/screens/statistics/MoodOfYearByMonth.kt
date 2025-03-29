package com.ideasapp.petemotions.presentation.ui.screens.statistics

import MoodPlot
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear

@Composable
fun MoodOfYearByMonth(
    moodOfYear: MoodOfYear,
    modifier: Modifier = Modifier
) {
    val coordinates = mapOf(
        1 to moodOfYear.januaryData,
        2 to moodOfYear.februaryData,
        3 to moodOfYear.marchData,
        4 to moodOfYear.aprilData,
        5 to moodOfYear.mayData,
        6 to moodOfYear.juneData,
        7 to moodOfYear.julyData,
        8 to moodOfYear.augustData,
        9 to moodOfYear.septemberData,
        10 to moodOfYear.octoberData,
        11 to moodOfYear.novemberData,
        12 to moodOfYear.decemberData
    )
    var expanded = remember { mutableStateOf(true) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                Text("2025", fontSize=18.sp, modifier = Modifier.padding(10.dp))
                Text("2026", fontSize=18.sp, modifier = Modifier.padding(10.dp))
                Divider()
                Text("2027", fontSize=18.sp, modifier = Modifier.padding(10.dp))
            }
            MoodPlot(
                coordinates = coordinates,
                modifier = Modifier.fillMaxSize(),
                xMaxValue = 12,
                yMaxValue = 100
            )
        }
    }
}