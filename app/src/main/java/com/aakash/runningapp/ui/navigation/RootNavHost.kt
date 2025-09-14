package com.aakash.runningapp.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aakash.runningapp.ui.appcomponent.snackbar.ObserveAsEvents
import com.aakash.runningapp.ui.appcomponent.snackbar.SnackbarManager
import com.aakash.runningapp.ui.navigation.topappbar.AppTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun RootNavHost(isAuthenticated: Boolean) {
    SharedTransitionLayout {

        val topAppbarTitle = remember { mutableStateOf("") }
        val topAppBarState = rememberTopAppBarState()
        val barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)
        val snackbarHostState = remember { SnackbarHostState() }

        val showTopBarState = rememberSaveable { (mutableStateOf(false)) }

        val coroutineScope = rememberCoroutineScope()
        val rootNavHostController = rememberNavController()
        val rootNavBackStackEntry by rootNavHostController.currentBackStackEntryAsState()

        ObserveAsEvents(
            flow = SnackbarManager.events,
            snackbarHostState
        ) { event ->
            coroutineScope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()

                val result = snackbarHostState.showSnackbar(
                    message = event.message,
                    actionLabel = event.action?.name,
                    duration = SnackbarDuration.Short
                )

                if (result == SnackbarResult.ActionPerformed) {
                    event.action?.action?.invoke()
                }
            }

            when (rootNavBackStackEntry?.destination?.route) {
                AppScreen.Main.Dashboard.route -> {
                    showTopBarState.value = true
                }

                AppScreen.Auth.Registration.route -> {
                    showTopBarState.value = false
                }

                AppScreen.Main.Profile.route -> {
                    showTopBarState.value = false
                }

                else -> {
                    showTopBarState.value = false
                }
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(rememberNestedScrollInteropConnection()),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                if (showTopBarState.value) {
                    AppTopBar(
                        topAppbarTitle.value,
                        barScrollBehavior,
                        onActionCameraClick = {

                        }
                    )
                } else {
                    Box {}
                }
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                NavHost(
                    navController = rootNavHostController,
                    startDestination = if (isAuthenticated) AppScreen.Main.route else AppScreen.Auth.route,
                    enterTransition = {
                        // you can change whatever you want transition
                        EnterTransition.None
                    },
                    exitTransition = {
                        // you can change whatever you want transition
                        ExitTransition.None
                    }
                ) {
                    authNavGraph(
                        rootNavHostController
                    )
                    mainNavGraph(
                        rootNavHostController
                    )
                }
            }
        }
    }
}