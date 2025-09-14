package com.aakash.runningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.aakash.runningapp.ui.navigation.RootNavHost
import com.aakash.runningapp.ui.theme.RunningAppTheme

class MainActivity : ComponentActivity() {
    private var isAuthenticated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        isAuthenticated = false
        setContent {
            RunningAppTheme {
                MainScreenContent(isAuthenticated)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
val localSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MainScreenContent(
    isAuthenticated: Boolean
) {

    SharedTransitionLayout {
        CompositionLocalProvider(
            localSharedTransitionScope provides this
        ) {

            RootNavHost(isAuthenticated)
        }


    }
}
