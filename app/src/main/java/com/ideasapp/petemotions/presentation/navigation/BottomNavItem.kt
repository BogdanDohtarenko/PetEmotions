package com.ideasapp.petemotions.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String,val icon:ImageVector,val label: String) {
    object Statistics : BottomNavItem(ROUTE_STATISTIC, Icons.Default.Home, "Statistics")
    object Calendar : BottomNavItem(ROUTE_CALENDAR, Icons.Default.Search, "Calendar")
    object Timetable : BottomNavItem(ROUTE_TIMETABLE, Icons.Default.Person, "Timetable")

    companion object {
        const val ROUTE_STATISTIC = "Statistics"
        const val ROUTE_CALENDAR = "Calendar"
        const val ROUTE_TIMETABLE = "Timetable"
    }
}