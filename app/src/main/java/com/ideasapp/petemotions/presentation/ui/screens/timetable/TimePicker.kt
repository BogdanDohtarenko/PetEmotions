package com.ideasapp.petemotions.presentation.ui.screens.timetable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.presentation.ui.reusableElements.Border
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.util.PickerUtil.COUNT_OF_VISIBLE_ITEMS
import com.ideasapp.petemotions.presentation.util.PickerUtil.ITEM_HEIGHT
import com.ideasapp.petemotions.presentation.util.PickerUtil.LIST_HEIGHT
import com.ideasapp.petemotions.presentation.util.PickerUtil.getTimeDefaultStr

@Composable
fun HourPicker(
    initial: Int,
    onTimeChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedHour by remember { mutableIntStateOf(initial) }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initial)

    val list by remember {
        mutableStateOf(mutableListOf<String>().apply {
            repeat(COUNT_OF_VISIBLE_ITEMS / 2) { add("") }
            for (hour in 0..23) {
                add(hour.getTimeDefaultStr())
            }
            repeat(COUNT_OF_VISIBLE_ITEMS / 2) { add("") }
        })
    }

    Box(
        modifier = modifier.height(LIST_HEIGHT.dp),
        contentAlignment = Alignment.Center
    ) {
        Border(itemHeight = ITEM_HEIGHT.dp, color = Color.Black)

        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            itemsIndexed(list) { _, item ->
                Box(
                    modifier = Modifier.fillParentMaxHeight(1f / COUNT_OF_VISIBLE_ITEMS),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        fontSize = 19.sp,
                        color = MainTheme.colors.singleTheme
                    )
                }
            }
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress && listState.firstVisibleItemScrollOffset != 0) {
            val targetIndex = listState.firstVisibleItemIndex
            listState.animateScrollToItem(targetIndex)
        }
    }

    val offset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    LaunchedEffect(offset) {
        val newHourIndex = listState.firstVisibleItemIndex + COUNT_OF_VISIBLE_ITEMS / 2
        if (newHourIndex in 0..24) {
            if (newHourIndex != selectedHour) {
                onTimeChange(newHourIndex - 1)
                selectedHour = newHourIndex
            }
        }
    }
}

@Composable
fun MinutePicker(
    initial: Int,
    onTimeChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMinute by remember { mutableIntStateOf(initial) }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initial)

    val list by remember {
        mutableStateOf(mutableListOf<String>().apply {
            repeat(COUNT_OF_VISIBLE_ITEMS / 2) { add("") }
            for (minute in 0..59) {
                add(minute.getTimeDefaultStr())
            }
            repeat(COUNT_OF_VISIBLE_ITEMS / 2) { add("") }
        })
    }

    Box(
        modifier = modifier.height(LIST_HEIGHT.dp),
        contentAlignment = Alignment.Center
    ) {
        Border(itemHeight = ITEM_HEIGHT.dp, color = Color.Black)

        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            itemsIndexed(list) { _, item ->
                Box(
                    modifier = Modifier.fillParentMaxHeight(1f / COUNT_OF_VISIBLE_ITEMS),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        fontSize = 19.sp,
                        color = MainTheme.colors.singleTheme
                    )
                }
            }
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress && listState.firstVisibleItemScrollOffset != 0) {
            val targetIndex = listState.firstVisibleItemIndex
            listState.animateScrollToItem(targetIndex)
        }
    }

    val offset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    LaunchedEffect(offset) {
        val newIndex = listState.firstVisibleItemIndex + COUNT_OF_VISIBLE_ITEMS / 2
        if (newIndex in 0..60) {
            if (newIndex != selectedMinute) {
                onTimeChange(newIndex - 1)
                selectedMinute = newIndex
            }
        }
    }
}
