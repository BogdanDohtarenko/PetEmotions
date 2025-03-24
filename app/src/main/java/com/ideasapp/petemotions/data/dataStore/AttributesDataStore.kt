package com.ideasapp.petemotions.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object AttributesDataStore {
    private const val ATTRIBUTES_DATA_STORE_NAME = "attributes_list"
    private val ATTRIBUTES_KEY = stringSetPreferencesKey(ATTRIBUTES_DATA_STORE_NAME)

    private val Context.dataStore by preferencesDataStore(ATTRIBUTES_DATA_STORE_NAME)
    private val gson = Gson()

    suspend fun saveAttributes(context:Context, pets:List<DayAttribute>) {
        val attributeStrings = pets.map {gson.toJson(it)} //store with set container
        context.dataStore.edit {preferences->
            preferences[ATTRIBUTES_KEY] = attributeStrings.toSet()
        }
    }

    fun getAttributesFlow(context:Context):Flow<List<DayAttribute>> {
        return context.dataStore.data.map {preferences->
            val attributeStrings = preferences[ATTRIBUTES_KEY] ?: emptySet()
            attributeStrings.map {gson.fromJson(it, DayAttribute::class.java)}
        }
    }
}