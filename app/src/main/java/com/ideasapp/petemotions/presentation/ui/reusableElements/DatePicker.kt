package com.ideasapp.petemotions.presentation.ui.reusableElements

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.util.PickerUtil.COUNT_OF_VISIBLE_ITEMS
import com.ideasapp.petemotions.presentation.util.PickerUtil.ITEM_HEIGHT
import com.ideasapp.petemotions.presentation.util.PickerUtil.LIST_HEIGHT
import com.ideasapp.petemotions.presentation.util.PickerUtil.getDateStringWithWeekOfDay
import java.time.LocalDate

@Composable
fun DatePicker(
    initialDate: LocalDate,
    onDateChange: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dateToday = remember { LocalDate.now() }
    var selectedDate by remember { mutableStateOf(initialDate) }
    val initialDaysIndexItem = remember {
        mutableIntStateOf((initialDate.toEpochDay() - dateToday.toEpochDay()).toInt())
    }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialDaysIndexItem.intValue)

    val list by remember {
        mutableStateOf(mutableListOf<String>().apply {
            (1..(COUNT_OF_VISIBLE_ITEMS / 2)).forEach { _ -> add("") }
            for (i in 0..367) {
                add(dateToday.plusDays(i.toLong()).getDateStringWithWeekOfDay(context))
            }
            (1..(COUNT_OF_VISIBLE_ITEMS / 2)).forEach { _ -> add("") }
        })
    }

    Box(
        modifier = modifier.height(LIST_HEIGHT.dp),
        contentAlignment = Alignment.Center
    ) {
        Border(itemHeight = ITEM_HEIGHT.dp, color = Color.Black)

        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            itemsIndexed(list) {_, it ->
                Box(
                    modifier = Modifier.fillParentMaxHeight(1f / COUNT_OF_VISIBLE_ITEMS),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        fontSize = 19.sp,
                        color = MainTheme.colors.singleTheme
                    )
                }
            }
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress && listState.firstVisibleItemScrollOffset % ITEM_HEIGHT != 0f) {
            listState.animateScrollToItem(listState.firstVisibleItemIndex)
        }
    }

    val offset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    LaunchedEffect(offset) {
        val newValueIndex = listState.firstVisibleItemIndex + COUNT_OF_VISIBLE_ITEMS / 2
        if (newValueIndex in 0..list.size) {
            val newDate = dateToday.plusDays(newValueIndex.toLong() - 1)
            if (newDate != selectedDate) {
                onDateChange(newDate)
                selectedDate = newDate
            }
        }
    }
}
