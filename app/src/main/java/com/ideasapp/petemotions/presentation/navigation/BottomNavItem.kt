package com.ideasapp.petemotions.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.ideasapp.petemotions.R

const val ROUTE_STATISTIC = "Statistics"
const val ROUTE_CALENDAR = "Calendar"
const val ROUTE_TIMETABLE = "Timetable"

enum class BottomNavItem(val route: String,val iconId: Int, val label: String, val id: Int) {
    Statistics(ROUTE_STATISTIC, R.drawable.ic_statistics, "Statistics", 0),
    Calendar(ROUTE_CALENDAR, R.drawable.ic_calendar, "Calendar", 1),
    Timetable(ROUTE_TIMETABLE, R.drawable.ic_timetable, "Timetable", 2)
}