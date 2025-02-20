package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ideasapp.petemotions.presentation.navigation.BottomNavItem


//TODO try deepLinks
@Composable
fun NavigationHost(
    navController:NavHostController,
    statisticsScreenContent: @Composable () -> Unit,
    calendarScreenContent: @Composable () -> Unit,
    timetableScreenContent: @Composable () -> Unit,
) {
    NavHost(navController, startDestination = BottomNavItem.Statistics.route) {
        composable(BottomNavItem.Statistics.route) {
            statisticsScreenContent()
        }
        composable(BottomNavItem.Calendar.route) {
            calendarScreenContent()
        }
        composable(BottomNavItem.Timetable.route) {
            timetableScreenContent()
        }
    }
}