package com.ideasapp.petemotions.presentation.ui.reusableElements

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ideasapp.petemotions.presentation.navigation.BottomNavItem

@Composable
fun BottomNavigationBar(navController:NavController) {
    BottomNavigation() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        BottomNavigationItem(
            selected = currentRoute == BottomNavItem.Home.route,
            onClick = {
                navController.navigate(BottomNavItem.Home.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(BottomNavItem.Home.icon, contentDescription = null) },
            label = { Text(BottomNavItem.Home.label) }
        )
        BottomNavigationItem(
            selected = currentRoute == BottomNavItem.Search.route,
            onClick = {
                navController.navigate(BottomNavItem.Search.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(BottomNavItem.Search.icon, contentDescription = null) },
            label = { Text(BottomNavItem.Search.label) }
        )
        BottomNavigationItem(
            selected = currentRoute == BottomNavItem.Profile.route,
            onClick = {
                navController.navigate(BottomNavItem.Profile.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(BottomNavItem.Profile.icon, contentDescription = null) },
            label = { Text(BottomNavItem.Profile.label) }
        )
    }
}