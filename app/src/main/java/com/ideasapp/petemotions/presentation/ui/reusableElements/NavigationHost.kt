package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ideasapp.petemotions.presentation.navigation.BottomNavItem


@Composable
fun NavigationHost(navController:NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            Text("Home")
        }
        composable(BottomNavItem.Search.route) {
            Text("Search")
        }
        composable(BottomNavItem.Profile.route) {
            Text("Profile")
        }
    }
}