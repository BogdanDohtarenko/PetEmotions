package com.ideasapp.petemotions.presentation.ui.screens.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.presentation.activity.MainActivity
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.CALENDAR_LOG_TAG
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DayInfoEdit(
    onSaveDayInfoClick: (DayItemInfo) -> Unit,
    exitCallback: () -> Unit,
    dateItem: CalendarUiState.Date?,
    petId: Int?,
    optionalAttributesFood: List<DayAttribute>?,
    optionalAttributesHealth: List<DayAttribute>?,
    optionalAttributesEvents: List<DayAttribute>?,
) {
    if (dateItem == null) throw RuntimeException("Date to DayInfoEdit = null")
    if (petId == null) throw RuntimeException("petId = null")

    val moodState = remember { mutableIntStateOf(dateItem.dayInfoItem.mood ?: MainActivity.MOOD_STATE_NORMAL) }

    val textColor =  MaterialTheme.colorScheme.onBackground
    val dateLocalDate = LocalDate.ofEpochDay(dateItem.dayInfoItem.date)
    val attributesFood = listOf(
        DayAttribute(Icons.AutoMirrored.Default.Send, "opt 1"),
        DayAttribute(Icons.AutoMirrored.Default.Send, "opt 2"),
        DayAttribute(Icons.AutoMirrored.Default.Send, "opt 3"),
        DayAttribute(Icons.AutoMirrored.Default.Send, "opt 4"),
    )
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .background(MainTheme.colors.singleTheme)
        .fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {
            HeaderForDay(
                dateLocalDate = dateLocalDate,
                exitCallback =  exitCallback
            )
            ChooseMoodBox(
                onClick = { moodState.intValue = it },
                moodState = moodState
            )
            //TODO add attribute choosing
            ChooseDayAttributesBox(
                textColor = textColor,
                titleOfBox = "Health",
                dayAttributesForFirstRow = attributesFood,
                dayAttributesOptional = optionalAttributesHealth) // Health
            ChooseDayAttributesBox(
                textColor = textColor,
                titleOfBox = "Food",
                dayAttributesForFirstRow = attributesFood,
                dayAttributesOptional = optionalAttributesFood) // Food
            ChooseDayAttributesBox(
                textColor = textColor,
                titleOfBox = "Events",
                dayAttributesForFirstRow = attributesFood,
                dayAttributesOptional = optionalAttributesEvents) // Events
            //TODO
            // FOOD
            // EVENTS
            // HEALTH
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val newDayInfo = DayItemInfo(date = dateItem.dayInfoItem.date, petId = petId, mood = moodState.intValue)
                    onSaveDayInfoClick(newDayInfo)
                    exitCallback()
                    Log.d(CALENDAR_LOG_TAG, "item saved: $newDayInfo")
                },
                colors = ButtonColors(
                    containerColor = MainTheme.colors.mainColor,
                    contentColor = MainTheme.colors.singleTheme,
                    disabledContentColor = MainTheme.colors.singleTheme,
                    disabledContainerColor = MainTheme.colors.mainColor
                )
            ) {
                Text(text = "save")
            }
        }
    }
}

//TODO divide into different files
@Composable
private fun ChooseDayAttributesBox(
    textColor: Color,
    titleOfBox: String,
    dayAttributesForFirstRow: List<DayAttribute>,
    dayAttributesOptional : List<DayAttribute>?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 14.dp)
            .background(Color.Gray)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = titleOfBox,
                color = textColor,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    dayAttributesForFirstRow.take(4).forEach { attribute ->
                        AttributeItem(
                            imageVector = attribute.imageVector,
                            textColor = textColor,
                            title = attribute.title
                        )
                    }
                }
                //optional rows
                dayAttributesOptional
                    ?.chunked(4)
                    ?.forEach { rowAttributes ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (i in 0 until 4) {
                                val attribute = rowAttributes.getOrNull(i)
                                if (attribute != null) {
                                    AttributeItem(
                                        imageVector = attribute.imageVector,
                                        textColor = textColor,
                                        title = attribute.title
                                    )
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AttributeItem(
                        imageVector = Icons.AutoMirrored.Default.List,
                        textColor = textColor,
                        title = "opt+"
                    )
                }
            }
        }
    }
}

@Composable
private fun ChooseMoodBox(
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

@Composable
private fun HeaderForDay(dateLocalDate : LocalDate,  exitCallback: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        IconButton(onClick = exitCallback, modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back",
                tint = MainTheme.colors.mainColor
            )
        }
        Text(
            "${dateLocalDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())},"+" ${dateLocalDate.dayOfMonth}"+" ${dateLocalDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())}",
            color = MainTheme.colors.mainColor,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .weight(8f)
                .align(Alignment.CenterVertically)
                .padding(80.dp, 10.dp)
        )
    }
}

@Composable
fun AttributeItem(
    imageVector: ImageVector,
    textColor: Color,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = title,
            tint = textColor,
            modifier = Modifier
                .size(14.dp)
                .padding(bottom = 4.dp)
        )
        Text(
            text = title,
            color = textColor,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}