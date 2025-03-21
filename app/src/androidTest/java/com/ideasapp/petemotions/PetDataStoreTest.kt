package com.ideasapp.petemotions

// Kotlin Coroutines
import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.ideasapp.petemotions.data.dataStore.PetDataStore
import com.ideasapp.petemotions.domain.entity.calendar.Pet

@ExperimentalCoroutinesApi
class PetDataStoreTest {

}