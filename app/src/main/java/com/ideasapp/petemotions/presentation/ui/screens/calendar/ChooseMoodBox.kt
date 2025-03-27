package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(unselectedColor)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Bad
            MoodOption(
                text = "B",
                isSelected = moodStateValue == MainActivity.MOOD_STATE_BAD,
                onClick = { onClick(MainActivity.MOOD_STATE_BAD) },
                selectedColor = selectedColor,
                unselectedColor = unselectedColor
            )

            // Normal
            MoodOption(
                text = "N",
                isSelected = moodStateValue == MainActivity.MOOD_STATE_NORMAL,
                onClick = { onClick(MainActivity.MOOD_STATE_NORMAL) },
                selectedColor = selectedColor,
                unselectedColor = unselectedColor
            )

            // Good
            MoodOption(
                text = "G",
                isSelected = moodStateValue == MainActivity.MOOD_STATE_GOOD,
                onClick = { onClick(MainActivity.MOOD_STATE_GOOD) },
                selectedColor = selectedColor,
                unselectedColor = unselectedColor
            )
        }
    }
}

@Composable
private fun MoodOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color,
    unselectedColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(if (isSelected) selectedColor else Color.Transparent)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            color = if (isSelected) unselectedColor else selectedColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}