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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.presentation.ui.reusableElements.FoldableBox
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.AttributesEditBox
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Composable
fun DayInfoEdit(
    //entities
    petId: Int?, // current pet
    dateItem: CalendarUiState.Date?, // current date
    //lists
    possibleIconsList: List<Int>,
    dayAttributesFlow: Flow<List<DayAttribute>>, // attribute lists
    //lambdas
    onAddAttributeClick : (DayAttribute) -> Unit, //on attribute save
    onDeleteAttributeClick : (DayAttribute) -> Unit, //on attribute delete
    exitCallback: () -> Unit, //exit
    onSaveDayInfoClick: (DayItemInfo) -> Unit, // on save date
) {
    val dayAttributesList = dayAttributesFlow.collectAsState(initial = listOf())
    if (dateItem == null) throw RuntimeException("Date to DayInfoEdit = null")
    if (petId == null) throw RuntimeException("petId = null")
    val textColor = MaterialTheme.colorScheme.onBackground
    val dateLocalDate = LocalDate.ofEpochDay(dateItem.dayInfoItem.date)
    //States
    val moodState = remember { mutableIntStateOf(dateItem.dayInfoItem.mood ?: DayItemInfo.NORMAL_MOOD) }
    val editFoodAttributeState = remember { mutableStateOf(false) }
    val editEventsAttributeState = remember { mutableStateOf(false) }
    val editHealthAttributeState = remember { mutableStateOf(false) }
    val chosenAttributesList = remember { mutableStateOf(dateItem.dayInfoItem.attributeNames.toMutableList()) }
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
            // TODO guide (how to delete add etc.)
            MoodAttributesElement(
                DayAttribute.ATTRIBUTE_TYPE_HEALTH,
                editHealthAttributeState,
                textColor,
                possibleIconsList,
                dayAttributesList.value.filter { it.type == DayAttribute.ATTRIBUTE_TYPE_HEALTH },
                chosenAttributesList.value,
                onAddAttributeClick,
                onDeleteAttributeClick,
                onChooseAttributeClick = { newAddedAttribute ->
                    Log.d("AddAttributes","newAddedAttribute: $newAddedAttribute")
                    Log.d("AddAttributes","chosenAttributesList: $chosenAttributesList")
                    chosenAttributesList.value = chosenAttributesList.value.toMutableList().apply {
                        if (!chosenAttributesList.value.contains(newAddedAttribute)) {
                            add(newAddedAttribute)
                        } else {
                            remove(newAddedAttribute)
                        }
                    }
                }
            )
            MoodAttributesElement(
                DayAttribute.ATTRIBUTE_TYPE_FOOD,
                editFoodAttributeState,
                textColor,
                possibleIconsList,
                dayAttributesList.value.filter { it.type == DayAttribute.ATTRIBUTE_TYPE_FOOD },
                chosenAttributesList.value,
                onAddAttributeClick,
                onDeleteAttributeClick,
                onChooseAttributeClick = { newAddedAttribute ->
                    Log.d("AddAttributes","newAddedAttribute: $newAddedAttribute")
                    Log.d("AddAttributes","chosenAttributesList: $chosenAttributesList")
                    chosenAttributesList.value = chosenAttributesList.value.toMutableList().apply {
                        if (!chosenAttributesList.value.contains(newAddedAttribute)) {
                            add(newAddedAttribute)
                        } else {
                            remove(newAddedAttribute)
                        }
                    }
                }
            )
            MoodAttributesElement(
                DayAttribute.ATTRIBUTE_TYPE_EVENTS,
                editEventsAttributeState,
                textColor,
                possibleIconsList,
                dayAttributesList.value.filter { it.type == DayAttribute.ATTRIBUTE_TYPE_EVENTS },
                chosenAttributesList.value,
                onAddAttributeClick,
                onDeleteAttributeClick,
                onChooseAttributeClick = { newAddedAttribute ->
                    Log.d("AddAttributes","newAddedAttribute: $newAddedAttribute")
                    Log.d("AddAttributes","chosenAttributesList: $chosenAttributesList")
                    chosenAttributesList.value = chosenAttributesList.value.toMutableList().apply {
                        if (!chosenAttributesList.value.contains(newAddedAttribute)) {
                            add(newAddedAttribute)
                        } else {
                            remove(newAddedAttribute)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = {
                    val newDayInfo = DayItemInfo(
                        date = dateItem.dayInfoItem.date,
                        petId = petId,
                        mood = moodState.intValue,
                        attributeNames = chosenAttributesList.value,
                    )
                    onSaveDayInfoClick(newDayInfo)
                    exitCallback()
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

@Composable
private fun MoodAttributesElement(
    attributeBoxType: String,
    editAttributeState : MutableState<Boolean>,
    textColor : Color,
    possibleIconsList: List<Int>,
    dayAttributesList: List<DayAttribute>,
    chosenAttributesList: List<String>,
    onAddAttributeClick: (DayAttribute) -> Unit,
    onDeleteAttributeClick: (DayAttribute) -> Unit,
    onChooseAttributeClick: (String) -> Unit,
) {
    Spacer(modifier = Modifier.height(18.dp))
    if (!editAttributeState.value) {
        FoldableBox(titleText = attributeBoxType, isExpandedByDefault = true) {
            ChooseDayAttributesBox(
                textColor = textColor,
                dayAttributesList = dayAttributesList,
                editAttributeState = editAttributeState,
                chosenAttributesList = chosenAttributesList,
                onChooseAttributeClick = onChooseAttributeClick,
            )
        }
    } else {
        AttributesEditBox(
            titleText = attributeBoxType,
            textColor = MainTheme.colors.singleTheme,
            attributeBoxType = attributeBoxType,
            addAttributeState = editAttributeState,
            possibleIconsList = possibleIconsList,
            dayAttributesList = dayAttributesList,
            onAddAttributeClick = { attribute -> onAddAttributeClick(attribute) },
            onDeleteAttributeClick = onDeleteAttributeClick,
        )
    }
}