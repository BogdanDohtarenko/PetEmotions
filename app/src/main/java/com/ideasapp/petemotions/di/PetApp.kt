package com.ideasapp.petemotions.di

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PetApp: Application() {
        override fun onCreate() {
            super.onCreate()
            val configuration = Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build()
            WorkManager.initialize(this, configuration)
        }
    }