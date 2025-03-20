package com.ideasapp.petemotions.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object PetDataStore {
    private const val PET_DATA_STORE_NAME = "pets_list"
    private val PETS_KEY = stringSetPreferencesKey(PET_DATA_STORE_NAME)

    private val Context.dataStore by preferencesDataStore(PET_DATA_STORE_NAME)
    private val gson = Gson()

    suspend fun savePets(context: Context, pets: List<Pet>) {
        val petStrings = pets.map { gson.toJson(it) }
        //store with set container
        context.dataStore.edit { preferences ->
            preferences[PETS_KEY] = petStrings.toSet()
        }
    }

    fun getPetsFlow(context: Context): Flow<List<Pet>> {
        return context.dataStore.data.map { preferences ->
            val petStrings = preferences[PETS_KEY] ?: emptySet()
            petStrings.map { gson.fromJson(it, Pet::class.java) }
        }
    }
}