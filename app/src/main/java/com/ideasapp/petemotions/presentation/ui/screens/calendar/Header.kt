package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ideasapp.petemotions.R
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.util.getDisplayName
import java.time.YearMonth

@Composable
fun Header(
    yearMonth: YearMonth,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
) {
    Row {
        IconButton(
            onClick = { onPreviousMonthButtonClicked.invoke(yearMonth.minusMonths(1))},
            Modifier.background(color = MainTheme.colors.singleTheme)
            ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                tint = MainTheme.colors.mainColor,
                contentDescription = stringResource(id = R.string.back)
            )
        }
        Text(
            text = yearMonth.getDisplayName(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MainTheme.colors.mainColor,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = {
            onNextMonthButtonClicked.invoke(yearMonth.plusMonths(1))
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                tint = MainTheme.colors.mainColor,
                contentDescription = stringResource(id = R.string.next)
            )
        }
    }
}