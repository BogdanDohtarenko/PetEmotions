package com.ideasapp.petemotions.presentation.ui.screens.statistics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.presentation.ui.reusableElements.FoldableBox
import com.ideasapp.petemotions.presentation.ui.screens.calendar.TopButtonCalendarBar
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun StatisticsScreen(
    petId: MutableIntState,
    petsList: List<Pet>,
    onPetClick: (Int) -> Unit,
    moodPortion: MoodPortion?
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            TopButtonCalendarBar(
                pets = petsList,
                onPetClick = onPetClick,
                petId = petId
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            FoldableBox(
                titleText = "Mood portions",
                isExpandedByDefault = true
            ) {
                if(moodPortion == null) {
                    CircularProgressIndicator(
                        color = MainTheme.colors.mainColor,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    MoodPortionPlot(
                        moodPortion = moodPortion,
                        modifier = Modifier.fillMaxWidth()
                    )
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