package com.ideasapp.petemotions.di

import android.app.Application
import androidx.work.Configuration
import com.ideasapp.petemotions.presentation.util.services.DailyWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PetApp: Application(), Configuration.Provider {
    @Inject lateinit var workerFactory :DailyWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}