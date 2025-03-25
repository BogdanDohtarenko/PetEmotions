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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayAttributesViewModel @Inject constructor(
    private val addDayAttribute: AddDayAttributeUseCase,
    private val getDayAttributes: GetDayAttributesUseCase,
    private val deleteDayAttributes: DeleteDayAttributeUseCase,
) : ViewModel() {

    private val _attributesList = MutableStateFlow<List<DayAttribute>>(emptyList())
    val attributesList = _attributesList.asStateFlow() // Expose as read-only

    init {
        viewModelScope.launch {
            collectDayAttributesList()
        }
    }

    // Collect attributes from the use case
    private suspend fun collectDayAttributesList() {
        getDayAttributes().collect { list ->
            _attributesList.value = list
            Log.d(CALENDAR_LOG_TAG, "attributes list: $list")
        }
    }

    //Work with attributes
    //TODO add basic attributes one time after onBoarding
    fun onAddDayAttribute(dayAttribute: DayAttribute) {
        Log.d(CALENDAR_LOG_TAG, "onAddDayAttribute called with ${dayAttribute.title}, type: ${dayAttribute.type}")
        viewModelScope.launch {
            val currentList = _attributesList.value
            if (currentList.contains(dayAttribute)) {
                Log.d(CALENDAR_LOG_TAG,"DayAttribute already exists: ${dayAttribute.title}")
                return@launch
            }
            _attributesList.value = currentList + dayAttribute
            addDayAttribute(dayAttribute)
        }
    }
    fun onDeleteDayAttribute(dayAttribute: DayAttribute) {
        Log.d(CALENDAR_LOG_TAG, "onDeleteDayAttribute called with ${dayAttribute.title}")
        viewModelScope.launch {
            val currentList = _attributesList.value
            _attributesList.value = currentList.filter { it != dayAttribute }
            deleteDayAttributes(dayAttribute)
        }
    }

}