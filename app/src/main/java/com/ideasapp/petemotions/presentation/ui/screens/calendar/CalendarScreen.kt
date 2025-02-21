package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//TODO
// 3. week days
// 4. calendar will take info from phone calendar
@Composable
fun CalendarScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopButtonCalendarBar()
        MonthDropDownMenu()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("mon", Modifier.padding(10.dp))
            Text("tue", Modifier.padding(10.dp))
            Text("wed", Modifier.padding(10.dp))
            Text("thu", Modifier.padding(10.dp))
            Text("fri", Modifier.padding(10.dp))
            Text("sat", Modifier.padding(10.dp))
            Text("sun", Modifier.padding(10.dp))
        }
    }
}


