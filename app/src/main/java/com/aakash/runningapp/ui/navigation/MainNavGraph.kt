package com.aakash.runningapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.aakash.runningapp.ui.home.DashboardScreen

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.Main.Dashboard.route,
        route = AppScreen.Main.route
    ) {
        composable(route = AppScreen.Main.Dashboard.route) {
            DashboardScreen (){  }
        }
        composable(route = AppScreen.Main.Profile.route) {

        }
    }

}