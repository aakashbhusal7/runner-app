package com.aakash.runningapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RunningApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}