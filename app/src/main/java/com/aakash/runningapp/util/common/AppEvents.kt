package com.aakash.runningapp.util.common

sealed class AppEvents {
    data class SnackBarEvent(val message: String) : AppEvents()
    data class NavigateEvent(val route: String) : AppEvents()
}