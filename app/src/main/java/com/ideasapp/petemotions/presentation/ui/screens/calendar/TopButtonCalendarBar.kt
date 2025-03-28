package com.ideasapp.petemotions.presentation.ui.screens.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.R
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.CALENDAR_LOG_TAG
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme


@Composable
fun TopButtonCalendarBar(
    pets: List<Pet>,
    onPetClick: (Int) -> Unit,
    petId: MutableIntState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .background(color = MainTheme.colors.singleTheme, shape = CircleShape)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = stringResource(id = R.string.back), // TODO change icon to shadow of pet
                tint = Color.White
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.Center
        )  {
            items(
                items = pets,
                key = { it.id }
            ) { pet ->
                Text(
                    text = pet.name,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .background(
                            color =
                                if (petId.intValue == pet.id) MainTheme.colors.mainColor
                                else MainTheme.colors.singleTheme,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                        .clickable {
                            Log.d(CALENDAR_LOG_TAG, "pet: ${pet.id} clicked")
                            petId.intValue = pet.id
                            onPetClick(pet.id)
                        },
                    color =
                        if (petId.intValue == pet.id) MainTheme.colors.singleTheme
                        else MainTheme.colors.mainColor
                )
            }
        }
    }
}