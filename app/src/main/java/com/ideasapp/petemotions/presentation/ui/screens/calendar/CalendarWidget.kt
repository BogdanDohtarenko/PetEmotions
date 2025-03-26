package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.navigation.DragValue
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import java.time.YearMonth
import kotlin.math.roundToInt

//TODO improve calendar
// circle selection
@OptIn(ExperimentalMaterialApi::class,ExperimentalFoundationApi::class)
@Composable
fun CalendarWidget(
    days: Array<String>,
    yearMonth:YearMonth,
    dates: List<CalendarUiState.Date>,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    // Define parent box and draggable box dimensions
    val parentBoxWidth = 320.dp
    val boxSize = 50.dp
    val widthPx = with(density) { (parentBoxWidth - boxSize).toPx() }

    // Anchors for the swipe states
    val anchors = DraggableAnchors {
        -1f at -widthPx // Previous month
        0f at 0f        // Current month
        1f at widthPx   // Next month
    }

    // Remember the draggable state
    val state = rememberSaveable(saver = AnchoredDraggableState.Saver()) {
        AnchoredDraggableState(
            initialValue = 0f,
            anchors = anchors
        )
    }

    // Handle swipe completion
    LaunchedEffect(state.currentValue) {
        when (state.currentValue) {
            1f -> {
                onNextMonthButtonClicked(yearMonth)
                scope.launch { state.animateTo(0f) } // Reset state after swipe
            }
            -1f -> {
                onPreviousMonthButtonClicked(yearMonth)
                scope.launch { state.animateTo(0f) } // Reset state after swipe
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .anchoredDraggable(
                state = state,
                orientation = Orientation.Horizontal
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .offset {
                    IntOffset(
                        x = state.requireOffset().toInt(),
                        y = 0
                    )
                }
        ) {
            Row {
                repeat(days.size) {
                    val item = days[it]
                    DayItem(item,modifier = Modifier.weight(1f))
                }
            }
            Header(
                yearMonth = yearMonth,
                onPreviousMonthButtonClicked = onPreviousMonthButtonClicked,
                onNextMonthButtonClicked = onNextMonthButtonClicked
            ) //month
            Content(
                dates = dates,
                onDateClickListener = onDateClickListener,
            )
        }
    }
}

@Composable
fun Content(
    dates: List<CalendarUiState.Date>,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    Column {
        var index = 0
        repeat(6) {
            if (index >= dates.size) return@repeat
            Row(Modifier.padding(5.dp, 10.dp)) { //to expand rows on all screen
                repeat(7) {
                    val item = if (index < dates.size) dates[index] else CalendarUiState.Date.Empty
                    ContentItem(
                        date = item,
                        onClickListener = onDateClickListener,
                        modifier = Modifier
                            .weight(1f)

                    )
                    index++
                }
            }
        }
    }
}

@Composable
fun ContentItem(
    date: CalendarUiState.Date,
    onClickListener: (CalendarUiState.Date) -> Unit,
    modifier: Modifier = Modifier
) {
    val color = if (date.dayInfoItem.mood == null)
        MainTheme.colors.singleTheme.copy(alpha = 1f)
        else
        MainTheme.colors.mainColor
    Box(
        modifier = modifier
            .clickable {
                onClickListener(date)
            }
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
        ) {
            // TODO Get gray icon if not filled
            Text(
                text = date.dayInfoItem.mood?.toString() ?: "0", //set mood here
                style = MaterialTheme.typography.bodyMedium,
                color = color,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = date.dayOfMonth,
                fontSize = 16.sp,
                color = if(date.isSelected) {
                    MainTheme.colors.oppositeTheme
                } else {
                    MainTheme.colors.mainColor
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}