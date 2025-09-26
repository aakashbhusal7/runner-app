package com.aakash.runningapp.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.aakash.runningapp.repository.UserRepositoryImpl
import com.aakash.runningapp.shared_pref.SharedPrefHelperImpl
import com.aakash.runningapp.ui.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val sharedPrefHelper: SharedPrefHelperImpl
) : BaseViewModel() {

    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState = _dashboardState.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val userId = sharedPrefHelper.getLoggedInUserId()
            Log.d("userId","value= "+userId)
            if (userId != null && userId!=-1L) {
                userRepository.getUserById(userId).collect { userEntity ->
                    _dashboardState.update { old ->
                        old.copy(user = userEntity)
                    }
                }
            }
        }
    }
}