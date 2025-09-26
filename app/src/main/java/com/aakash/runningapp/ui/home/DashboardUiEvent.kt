package com.aakash.runningapp.ui.home

sealed class DashboardUiEvent {
    data object LoadUserData : DashboardUiEvent()
}