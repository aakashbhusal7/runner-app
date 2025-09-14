package com.aakash.runningapp.ui.navigation

private object AppRouter {

    const val AUTH = "auth"
    const val REGISTRATION = "registration"

    const val MAIN = "main"
    const val DASHBOARD = "dashboard"

    const val PROFILE = "profile"
}

private object ArgParams {

}

sealed class AppScreen(val route: String) {

    object Auth : AppScreen(AppRouter.AUTH) {
        object Registration : AppScreen(route = AppRouter.REGISTRATION)
    }

    object Main : AppScreen(AppRouter.MAIN) {
        object Dashboard : AppScreen(route = AppRouter.DASHBOARD)
        object Profile : AppScreen(route = AppRouter.PROFILE)
    }

}