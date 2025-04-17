package com.ideasapp.petemotions.presentation.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object GlobalPreferences {

    private val Context.dataStore by preferencesDataStore(name = "global_preferences")
    private val FIRST_LAUNCH_KEY_PERMISSION = booleanPreferencesKey("first_launch")
    private val GREETINGS_KEY = booleanPreferencesKey("greetings_screen")

    fun onBoardingLaunch(context: Context): Boolean {
        return runBlocking {
            val preferences = context.dataStore.data.first()
            preferences[GREETINGS_KEY] ?: true
        }
    }

    fun updateOnBoardingLaunch(context: Context) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[GREETINGS_KEY] = false
            }
        }
    }

    fun isFirstLaunch(context: Context): Boolean {
        return runBlocking {
            val preferences = context.dataStore.data.first()
            preferences[FIRST_LAUNCH_KEY_PERMISSION] ?: true
        }
    }

    fun updateFirstLaunch(context: Context) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[FIRST_LAUNCH_KEY_PERMISSION] = false
            }
        }
    }
}