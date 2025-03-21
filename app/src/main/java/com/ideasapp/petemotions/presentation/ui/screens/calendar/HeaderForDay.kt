package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun HeaderForDay(dateLocalDate : LocalDate, exitCallback: () -> Unit) {
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
                .padding(10.dp)
        )
    }
}