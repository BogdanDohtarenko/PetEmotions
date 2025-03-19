package com.ideasapp.petemotions.presentation.ui.reusableElements

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.activity.MainActivity
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
    dayInfoEditContent: @Composable (CalendarUiState.Date?,  () -> Unit, Int?) -> Unit,
) {
    //TODO amend animations
    NavHost(
        navController,
        startDestination = BottomNavItem.Calendar.route,
    ) {
        //STATISTIC
        composable(
            BottomNavItem.Statistics.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 100 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -100 }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -100 }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 100 }) + fadeOut() }
        ) {
            statisticsScreenContent()
        }
        //CALENDAR
        composable(
            BottomNavItem.Calendar.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 100 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -100 }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -100 }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 100 }) + fadeOut() }
        ) {
            calendarScreenContent()
        }
        //TIMETABLE
        composable(
            BottomNavItem.Timetable.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 100 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -100 }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -100 }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 100 }) + fadeOut() }
        ) {
            timetableScreenContent()
        }
        //EDIT DAY
        composable(
            route = NavItem.EditDay.route + "/{${NavItem.DATE_PARAM}}" + "/{${NavItem.PET_PARAM}}",
            arguments = listOf(
                navArgument(NavItem.DATE_PARAM) { type = NavType.StringType },
                navArgument(NavItem.PET_PARAM) { type = NavType.IntType }
            ), // StringType
            enterTransition = { slideInHorizontally(initialOffsetX = { 100 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -100 }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -100 }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 100 }) + fadeOut() }
        ) { stackEntry ->
            //Get args
            Log.d(MainActivity.CALENDAR_LOG_TAG,
                " date got in json: ${stackEntry.arguments?.getString(NavItem.DATE_PARAM)}")
            val dateJson = stackEntry.arguments?.getString(NavItem.DATE_PARAM)
            val petIdJson = stackEntry.arguments?.getInt(NavItem.PET_PARAM)
            //cast to CalendarUiState.Date
            val date = dateJson?.toCalendarUiStateDate()
            val petId = petIdJson?.toInt()

            dayInfoEditContent(date, { navController.popBackStack() }, petIdJson)
        }
    }
}