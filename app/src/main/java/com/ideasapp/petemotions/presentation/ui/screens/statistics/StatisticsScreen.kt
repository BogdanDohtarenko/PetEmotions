package com.ideasapp.petemotions.presentation.ui.screens.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.entity.stastistics.ChartModel
import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.presentation.ui.reusableElements.FoldableBox
import com.ideasapp.petemotions.presentation.ui.screens.calendar.TopButtonCalendarBar
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun StatisticsScreen(
    //state values
    petId: MutableIntState,
    petsList: List<Pet>,
    selectedYear: MutableIntState,
    years: List<Int>,
    onPetClick: (Int) -> Unit,
    openProfile: () -> Unit,
    //for plots and diagrams
    moodPortion: MoodPortion?,
    moodOfYear: MoodOfYear?,
    charts : List<ChartModel>?
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .verticalScroll(scrollState)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TopButtonCalendarBar(pets = petsList,
                        onPetClick = onPetClick,
                        petId = petId,
                        openProfile = openProfile
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight()
                ) {
                    FoldableBox(
                        titleText = "Mood portions",
                        isExpandedByDefault = true,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        if (moodPortion == null) {
                            CircularProgressIndicator(color = MainTheme.colors.mainColor,modifier = Modifier.align(Alignment.CenterHorizontally))
                        } else {
                            MoodPortionPlot(moodPortion = moodPortion,modifier = Modifier.fillMaxWidth())
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    FoldableBox(titleText = "Mood of year", isExpandedByDefault = true) {
                        if (moodOfYear == null) {
                            CircularProgressIndicator(color = MainTheme.colors.mainColor)
                        } else {
                            MoodOfYearByMonth(
                                moodOfYear = moodOfYear,
                                selectedYear = selectedYear,
                                years = years,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    FoldableBox(titleText = "Attributes diagram", isExpandedByDefault = true) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize().padding(8.dp)
                        ) {
                            if (charts == null) {
                                CircularProgressIndicator(color = MainTheme.colors.mainColor)
                            } else  {
                                AttributesDiagram (
                                    selectedYear = selectedYear,
                                    years = years,
                                    charts = charts,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    FoldableBox(titleText = "plot") {
                        Box(contentAlignment = Alignment.Center,modifier = Modifier.fillMaxWidth()) {
                            Text("plot")
                        }
                    }
                }
            }
    }
}