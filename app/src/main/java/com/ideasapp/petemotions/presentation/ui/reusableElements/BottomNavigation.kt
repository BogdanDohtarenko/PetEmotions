package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ideasapp.petemotions.presentation.navigation.BottomNavItem

//TODO add beautiful animations
@Composable
fun BottomNavigationBar(navController:NavController) {
    BottomNavigation() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        BottomNavigationItem(
            selected = currentRoute == BottomNavItem.Statistics.route,
            onClick = {
                navController.navigate(BottomNavItem.Statistics.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(BottomNavItem.Statistics.icon, contentDescription = null) },
            label = { Text(BottomNavItem.Statistics.label) }
        )
        BottomNavigationItem(
            selected = currentRoute == BottomNavItem.Calendar.route,
            onClick = {
                navController.navigate(BottomNavItem.Calendar.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(BottomNavItem.Calendar.icon, contentDescription = null) },
            label = { Text(BottomNavItem.Calendar.label) }
        )
        BottomNavigationItem(
            selected = currentRoute == BottomNavItem.Timetable.route,
            onClick = {
                navController.navigate(BottomNavItem.Timetable.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(BottomNavItem.Timetable.icon, contentDescription = null) },
            label = { Text(BottomNavItem.Timetable.label) }
        )
    }
}