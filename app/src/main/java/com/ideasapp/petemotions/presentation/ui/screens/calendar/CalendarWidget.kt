package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.R
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import java.time.YearMonth

//TODO improve calendar
// circle selection
@Composable
fun CalendarWidget(
    days: Array<String>,
    yearMonth:YearMonth,
    dates: List<CalendarUiState.Date>,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {


    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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
            val imageId = when (date.dayInfoItem.mood) {
                1 -> R.drawable.animal_good
                2 -> R.drawable.animal_normal
                3 -> R.drawable.animal_bad
                else -> null
            }
            // TODO Get gray icon if not filled
            if (imageId != null) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = date.dayInfoItem.mood.toString(),
                    modifier = Modifier
                        .size(45.dp)
                        .align(Alignment.CenterHorizontally),
                )
            } else {
                Text(
                    text = "0", //set mood here
                    style = MaterialTheme.typography.bodyMedium,
                    color = color,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Text(
                text = date.dayOfMonth,
                fontSize = 16.sp,
                color = if (date.isSelected) {
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