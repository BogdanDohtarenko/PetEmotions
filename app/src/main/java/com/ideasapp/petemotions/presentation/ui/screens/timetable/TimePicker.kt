package com.ideasapp.petemotions.presentation.ui.screens.timetable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.presentation.ui.reusableElements.Border
import com.ideasapp.petemotions.presentation.util.PickerUtil.COUNT_OF_VISIBLE_ITEMS
import com.ideasapp.petemotions.presentation.util.PickerUtil.ITEM_HEIGHT
import com.ideasapp.petemotions.presentation.util.PickerUtil.LIST_HEIGHT
import com.ideasapp.petemotions.presentation.util.PickerUtil.getTimeDefaultStr
import com.ideasapp.petemotions.presentation.util.PickerUtil.itemForScrollTo
import com.ideasapp.petemotions.presentation.util.PickerUtil.pixelsToDp

//TODO bug: cant scroll more than 21
@Composable
internal fun TimePicker(
    initialHour: Int,
    initialMinute: Int,
    onTimeChange: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val hoursState = rememberLazyListState(initialFirstVisibleItemIndex = initialHour)
    val minutesState = rememberLazyListState(initialFirstVisibleItemIndex = initialMinute)

    var selectedHour by remember { mutableStateOf(initialHour) }
    var selectedMinute by remember { mutableStateOf(initialMinute) }

    Row(
        modifier = modifier.height(LIST_HEIGHT.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Hours Picker
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Border(itemHeight = ITEM_HEIGHT.dp, color = Color.Gray)

            LazyColumn(state = hoursState, modifier = Modifier.fillMaxSize()) {
                itemsIndexed((0..23).toList()) { _, hour ->
                    Box(
                        modifier = Modifier.fillParentMaxHeight(1f / COUNT_OF_VISIBLE_ITEMS),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = hour.getTimeDefaultStr(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Text(
            text = ":",
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        // Minutes Picker
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Border(itemHeight = ITEM_HEIGHT.dp, color = Color.Gray)

            LazyColumn(state = minutesState, modifier = Modifier.fillMaxSize()) {
                itemsIndexed((0..59).toList()) { _, minute ->
                    Box(
                        modifier = Modifier.fillParentMaxHeight(1f / COUNT_OF_VISIBLE_ITEMS),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = minute.getTimeDefaultStr(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(hoursState.firstVisibleItemIndex, minutesState.firstVisibleItemIndex) {
        if (hoursState.firstVisibleItemIndex != selectedHour || minutesState.firstVisibleItemIndex != selectedMinute) {
            selectedHour = hoursState.firstVisibleItemIndex
            selectedMinute = minutesState.firstVisibleItemIndex
            onTimeChange(selectedHour, selectedMinute)
        }
    }
}

