package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ideasapp.petemotions.presentation.navigation.BottomNavItem


@Composable
fun NavigationHost(navController:NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Statistics.route) {
        composable(BottomNavItem.Statistics.route) {
            Text("Home")
        }
        composable(BottomNavItem.Calendar.route) {
            Text("Search")
        }
        composable(BottomNavItem.Timetable.route) {
            Text("Day Timetable")
        }
    }
}