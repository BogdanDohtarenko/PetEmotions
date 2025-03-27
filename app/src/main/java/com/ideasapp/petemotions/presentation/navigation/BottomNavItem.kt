package com.ideasapp.petemotions.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

const val ROUTE_STATISTIC = "Statistics"
const val ROUTE_CALENDAR = "Calendar"
const val ROUTE_TIMETABLE = "Timetable"

enum class BottomNavItem(val route: String,val icon:ImageVector, val label: String, val id: Int) {
    Statistics(ROUTE_STATISTIC, Icons.Default.Home, "Statistics", 0),
    Calendar(ROUTE_CALENDAR, Icons.Default.Search, "Calendar", 1),
    Timetable(ROUTE_TIMETABLE, Icons.Default.Person, "Timetable", 2)
}