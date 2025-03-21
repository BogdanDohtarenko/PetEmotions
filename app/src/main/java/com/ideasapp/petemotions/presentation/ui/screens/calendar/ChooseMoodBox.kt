package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.presentation.activity.MainActivity
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme


@Composable
fun ChooseMoodBox(
    onClick: (Int) -> Unit,
    moodState: MutableIntState
) {
    val moodStateValue = moodState.intValue
    val selectedColor = MainTheme.colors.mainColor
    val unselectedColor = MainTheme.colors.singleTheme
    Box(modifier = Modifier
        .fillMaxWidth(0.7f)
        .padding(vertical = 8.dp)
        .background(MainTheme.colors.singleTheme)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            //Change to images
            Text(
                "B",
                color = if (moodStateValue == MainActivity.MOOD_STATE_BAD)
                    unselectedColor
                else
                    selectedColor,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(20.dp, 8.dp)
                    .background(if (moodStateValue == MainActivity.MOOD_STATE_BAD) selectedColor
                    else unselectedColor)
                    .clickable {onClick(MainActivity.MOOD_STATE_BAD)}
            )
            Text(
                "N",
                color = if (moodStateValue == MainActivity.MOOD_STATE_NORMAL)
                    unselectedColor
                else
                    selectedColor,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(20.dp, 8.dp)
                    .background(if (moodStateValue == MainActivity.MOOD_STATE_NORMAL) selectedColor
                    else unselectedColor)
                    .clickable {onClick(MainActivity.MOOD_STATE_NORMAL)}
            )
            Text(
                "G",
                color = if (moodStateValue == MainActivity.MOOD_STATE_GOOD)
                    unselectedColor
                else
                    selectedColor,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(20.dp, 8.dp)
                    .background(if (moodStateValue == MainActivity.MOOD_STATE_GOOD) selectedColor
                    else unselectedColor)
                    .clickable {onClick(MainActivity.MOOD_STATE_GOOD)}
            )
        }
    }
}