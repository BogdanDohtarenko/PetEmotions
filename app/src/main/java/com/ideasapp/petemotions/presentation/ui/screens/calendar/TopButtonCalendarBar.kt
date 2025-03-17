package com.ideasapp.petemotions.presentation.ui.screens.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ideasapp.petemotions.R
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.CALENDAR_LOG_TAG
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun TopButtonCalendarBar(
    pets: List<Pet>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        //TODO filter button
        IconButton(
            onClick = { Log.d(CALENDAR_LOG_TAG, "filter clicked") },
            modifier = Modifier
                .background(color = MainTheme.colors.singleTheme, shape = CircleShape)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = stringResource(id = R.string.back),
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
                        .background(color = MainTheme.colors.spareContentColor, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    color = MainTheme.colors.singleTheme
                )
            }
        }
    }
}