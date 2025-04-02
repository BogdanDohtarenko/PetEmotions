package com.ideasapp.petemotions.presentation.ui.screens.statistics

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.R

@Composable
fun MoodPortionPlot(
    moodPortion: MoodPortion,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Days filled: ${moodPortion.allDaysCount}",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                MoodStatColumn(R.drawable.animal_good, "Good", moodPortion.goodPercents)
                MoodStatColumn(R.drawable.animal_normal, "Normal", moodPortion.normalPercents)
                MoodStatColumn(R.drawable.animal_bad, "Bad", moodPortion.badPercents)
            }
        }
    }
}

@Composable
fun MoodStatColumn(
    iconId: Int,
    moodLabel: String,
    percentage: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconId),contentDescription = moodLabel
        )
        Text(text = moodLabel, fontSize = 16.sp)
        Text(text = "$percentage%", fontSize = 16.sp)
    }
}