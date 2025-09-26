package com.aakash.runningapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.aakash.runningapp.shared_pref.SharedPrefHelperImpl
import com.aakash.runningapp.ui.appcomponent.BaseViewModel
import com.aakash.runningapp.ui.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferenceHelper: SharedPrefHelperImpl
) : BaseViewModel() {
    private val _currentRoute = mutableStateOf<String?>(AppScreen.Auth.route)
    val currentRoute: State<String?> = _currentRoute

    fun setCurrentRoute(route: String?) {
        _currentRoute.value = route
    }

    fun isUserAuthenticated(): Boolean {
        return preferenceHelper.getRegistrationStatus()
    }

}