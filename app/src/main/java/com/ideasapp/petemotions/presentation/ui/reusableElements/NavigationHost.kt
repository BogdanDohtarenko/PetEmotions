package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.navigation.BottomNavItem
import com.ideasapp.petemotions.presentation.navigation.NavItem
import com.ideasapp.petemotions.presentation.util.toCalendarUiStateDate


//TODO try deepLinks
@Composable
fun NavigationHost(
    navController:NavHostController,
    statisticsScreenContent: @Composable () -> Unit,
    calendarScreenContent: @Composable () -> Unit,
    timetableScreenContent: @Composable () -> Unit,
    dayInfoEditContent: @Composable (CalendarUiState.Date?,  () -> Unit) -> Unit,
) {
    NavHost(navController, startDestination = BottomNavItem.Calendar.route) {
        composable(BottomNavItem.Statistics.route) {
            statisticsScreenContent()
        }
        composable(BottomNavItem.Calendar.route) {
            calendarScreenContent()
        }
        composable(BottomNavItem.Timetable.route) {
            timetableScreenContent()
        }
        composable(
            route = NavItem.EditDay.route + "/{${NavItem.DATE_PARAM}}",
            arguments = listOf(navArgument(NavItem.DATE_PARAM) { type = NavType.StringType }) // StringType
        ) { stackEntry ->
            val dateJson = stackEntry.arguments?.getString(NavItem.DATE_PARAM)
            val date = dateJson?.toCalendarUiStateDate()

            dayInfoEditContent(date) {
                navController.popBackStack() // Возвращаемся назад при закрытии
            }
        }
    }
}