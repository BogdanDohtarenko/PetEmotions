package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ideasapp.petemotions.presentation.ui.reusableElements.BottomNavigationBar
import com.ideasapp.petemotions.presentation.ui.reusableElements.NavigationHost

@Composable
fun CalendarScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        modifier = Modifier.navigationBarsPadding(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            NavigationHost(
                navController = navController,
                statisticsScreenContent = { Text("statistics")},
                calendarScreenContent = { Text("calendar")},
                timetableScreenContent = { Text("timetable")}
            )
        }
    }
}
