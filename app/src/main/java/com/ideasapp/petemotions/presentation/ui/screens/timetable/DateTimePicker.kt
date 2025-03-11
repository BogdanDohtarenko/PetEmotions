package com.ideasapp.petemotions.presentation.ui.screens.timetable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.presentation.util.PickerUtil.COUNT_OF_VISIBLE_ITEMS
import com.ideasapp.petemotions.presentation.util.PickerUtil.ITEM_HEIGHT
import com.ideasapp.petemotions.presentation.util.PickerUtil.LIST_HEIGHT
import com.ideasapp.petemotions.presentation.util.PickerUtil.getTimeDefaultStr

//TODO resolve article
@Composable
internal fun TimeColumnPicker(
    initialValue: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialValue)

    val list by remember {
        mutableStateOf(mutableListOf<String>().apply {
            (1..(COUNT_OF_VISIBLE_ITEMS / 2)).forEach { _ -> add("") }
            for (i in range) add(i.getTimeDefaultStr())
            (1..(COUNT_OF_VISIBLE_ITEMS / 2)).forEach { _ -> add("") }
        })
    }

    var selectedValue by remember { mutableIntStateOf(initialValue) }
    var firstIndex by remember { mutableIntStateOf(0) }
    var lastIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier.height(LIST_HEIGHT.dp),
        contentAlignment = Alignment.Center
    ) {
        Border(itemHeight = ITEM_HEIGHT.dp, color = Color.LightGray)

        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            itemsIndexed(items = list) { index, it ->
                Box(
                    modifier = Modifier.fillParentMaxHeight(1f / COUNT_OF_VISIBLE_ITEMS),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

@Composable
internal fun Border(itemHeight: Dp, color: Color) {
    val width = 2.dp
    val strokeWidthPx = with(LocalDensity.current) { width.toPx() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .drawBehind {
                drawLine(
                    color = color,
                    strokeWidth = strokeWidthPx,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f)
                )

                drawLine(
                    color = color,
                    strokeWidth = strokeWidthPx,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height)
                )
            }
    ) {}
}