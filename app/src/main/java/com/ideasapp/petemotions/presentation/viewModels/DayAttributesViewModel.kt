package com.ideasapp.petemotions.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.use_case.dayAttributes.AddDayAttributeUseCase
import com.ideasapp.petemotions.domain.use_case.dayAttributes.DeleteDayAttributeUseCase
import com.ideasapp.petemotions.domain.use_case.dayAttributes.GetDayAttributesUseCase
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.CALENDAR_LOG_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayAttributesViewModel @Inject constructor(
    private val addDayAttribute :AddDayAttributeUseCase,
    private val getDayAttributes :GetDayAttributesUseCase,
    private val deleteDayAttributes :DeleteDayAttributeUseCase,
)  : ViewModel() {

    private val _attributesList = MutableStateFlow<List<DayAttribute>>(emptyList())
    private val attributesList = _attributesList.asStateFlow()

    init {
        viewModelScope.launch {
            collectDayAttributesList()
        }
    }

    //Work with attributes
    //TODO add basic attributes one time after onBoarding
    private suspend fun collectDayAttributesList() {
        val flowOfLists:Flow<List<DayAttribute>> = getDayAttributes()
        flowOfLists.collect { list ->
            _attributesList.value = list
            Log.d(CALENDAR_LOG_TAG, "attributes list: $list")
        }
    }
    fun onAddDayAttribute(dayAttribute: DayAttribute) {
        Log.d(CALENDAR_LOG_TAG, "onAddDayAttribute called with ${dayAttribute.title}, type: ${dayAttribute.type}")
        viewModelScope.launch {
            val currentList = _attributesList.value
            if (currentList.contains(dayAttribute)) {
                Log.d(CALENDAR_LOG_TAG, "DayAttribute already exists: ${dayAttribute.title}")
                return@launch
            }
            val updatedList = currentList + dayAttribute
            _attributesList.value = updatedList
            Log.d(CALENDAR_LOG_TAG, "Updated attributes list: ${_attributesList.value}")
            addDayAttribute(dayAttribute)
        }
    }
    fun onDeleteDayAttribute(dayAttribute :DayAttribute) {
        val currentList = _attributesList.value
        val updatedList = currentList + dayAttribute
        _attributesList.value = updatedList
        viewModelScope.launch {
            deleteDayAttributes(dayAttribute)
        }
    }
    fun getDayAttributesFood(): List<DayAttribute> {
        return attributesList.value.filter { it.type == DayAttribute.ATTRIBUTE_TYPE_FOOD }
    }
    fun getDayAttributesHealth(): List<DayAttribute> {
        return attributesList.value.filter { it.type == DayAttribute.ATTRIBUTE_TYPE_HEALTH }
    }
    fun getDayAttributesEvents(): List<DayAttribute> {
        return attributesList.value.filter { it.type == DayAttribute.ATTRIBUTE_TYPE_EVENTS }
    }
}