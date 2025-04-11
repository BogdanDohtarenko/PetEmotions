package com.ideasapp.petemotions.presentation.ui.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.stastistics.ChartModel
import com.ideasapp.petemotions.presentation.ui.reusableElements.ChartCirclePie
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.DropdownList

@Composable
fun AttributesDiagram(
    selectedYear:MutableIntState,
    years: List<Int>,
    charts: List<ChartModel>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DropdownList(
                itemList = years.map { it.toString() },
                selectedIndex = selectedYear,
                modifier = Modifier.padding(6.dp),
                onItemClick = { selectedYear.intValue = it }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ChartCirclePie(
                    modifier = Modifier,
                    charts = charts
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    charts.forEach {chart->
                        Row(horizontalArrangement = Arrangement.Start,verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier
                                .padding(4.dp)
                                .background(chart.color)
                                .height(8.dp)
                                .width(8.dp)) {}
                            Text(
                                text = chart.name,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}