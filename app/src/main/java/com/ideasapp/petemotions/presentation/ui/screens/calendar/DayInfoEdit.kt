package com.ideasapp.petemotions.presentation.ui.screens.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.presentation.activity.MainActivity
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.CALENDAR_LOG_TAG
import com.ideasapp.petemotions.presentation.ui.reusableElements.FoldableBox
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.AttributesEditBox
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import java.time.LocalDate

@Composable
fun DayInfoEdit(
    onSaveDayInfoClick: (DayItemInfo) -> Unit,
    exitCallback: () -> Unit,
    dateItem: CalendarUiState.Date?,
    petId: Int?,
    dayAttributesListFood: List<DayAttribute>,
    dayAttributesListEvents : List<DayAttribute>,
    dayAttributesListHealth: List<DayAttribute>,
) {
    if (dateItem == null) throw RuntimeException("Date to DayInfoEdit = null")
    if (petId == null) throw RuntimeException("petId = null")
    val textColor = MaterialTheme.colorScheme.onBackground
    val dateLocalDate = LocalDate.ofEpochDay(dateItem.dayInfoItem.date)
    //States
    val moodState = remember { mutableIntStateOf(dateItem.dayInfoItem.mood ?: MainActivity.MOOD_STATE_NORMAL) }
    val editFoodAttributeState = remember { mutableStateOf(false) }
    val editEventsAttributeState = remember { mutableStateOf(false) }
    val editHealthAttributeState = remember { mutableStateOf(false) }
    val chosenAttribute = remember { mutableStateOf<DayAttribute?>(null) }
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .background(MainTheme.colors.singleTheme)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(18.dp),
        ) {
            HeaderForDay(
                dateLocalDate = dateLocalDate,
                exitCallback = exitCallback
            )
            Spacer(modifier = Modifier.height(18.dp))
            ChooseMoodBox(
                onClick = { moodState.intValue = it },
                moodState = moodState
            )
            // TODO add attribute choosing
            // TODO set shaking when editing 
            Spacer(modifier = Modifier.height(18.dp))
            // Health
            if (!editHealthAttributeState.value) {
                FoldableBox(titleText = "Health", isExpandedByDefault = true) {
                    ChooseDayAttributesBox(
                        textColor = textColor,
                        addAttributeState = editHealthAttributeState,
                        dayAttributesList = dayAttributesListHealth,
                    )
                }
            } else {
                AttributesEditBox (
                    titleText = "Health",
                    textColor = MainTheme.colors.singleTheme,
                    addAttributeState = editHealthAttributeState,
                    dayAttributesList = dayAttributesListHealth,
                    onAddAttributeClick = { attribute -> Log.d(CALENDAR_LOG_TAG, "truing add: $attribute") } //TODO VIEW MODEL
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            // Food
            if (!editFoodAttributeState.value) {
                FoldableBox(titleText = "Food", isExpandedByDefault = true) {
                    ChooseDayAttributesBox(
                        textColor = textColor,
                        addAttributeState = editFoodAttributeState,
                        dayAttributesList = dayAttributesListFood,
                    )
                }
            } else {
                AttributesEditBox (
                    titleText = "Food",
                    textColor = MainTheme.colors.singleTheme,
                    addAttributeState = editFoodAttributeState,
                    dayAttributesList = dayAttributesListFood,
                    onAddAttributeClick = { attribute -> Log.d(CALENDAR_LOG_TAG, "truing add: $attribute") }
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            // Events
            if (!editEventsAttributeState.value) {
                FoldableBox(
                    titleText = "Events",
                    isExpandedByDefault = true
                ) {
                    ChooseDayAttributesBox(
                        textColor = textColor,
                        addAttributeState = editEventsAttributeState,
                        dayAttributesList = dayAttributesListEvents,
                    )
                }
            } else {
                AttributesEditBox (
                    titleText = "Events",
                    textColor = MainTheme.colors.singleTheme,
                    addAttributeState = editEventsAttributeState,
                    dayAttributesList = dayAttributesListEvents,
                    onAddAttributeClick = { attribute -> Log.d(CALENDAR_LOG_TAG, "truing add: $attribute") }
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = {
                    val newDayInfo = DayItemInfo(
                        date = dateItem.dayInfoItem.date,
                        petId = petId,
                        mood = moodState.intValue
                    )
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
                Text(text = "Save")
            }
        }
    }
}