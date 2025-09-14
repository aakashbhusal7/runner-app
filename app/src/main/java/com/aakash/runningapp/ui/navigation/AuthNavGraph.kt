package com.aakash.runningapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.aakash.runningapp.ui.registration.RegistrationScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = AppScreen.Auth.Registration.route,
        route = AppScreen.Auth.route
    ) {
        composable(
            route = AppScreen.Auth.Registration.route
        ) {
            RegistrationScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
    }
}