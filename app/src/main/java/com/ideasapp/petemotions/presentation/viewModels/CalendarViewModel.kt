package com.ideasapp.petemotions.presentation.viewModels

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.use_case.calendar.AddDayItemUseCase
import com.ideasapp.petemotions.domain.use_case.calendar.GetCalendarWithMood
import com.ideasapp.petemotions.presentation.activity.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarWithMood: GetCalendarWithMood,
    private val addDayItemUseCase: AddDayItemUseCase
)  : ViewModel() {

    private val petIdLD: MutableLiveData<Int> = MutableLiveData(0)

    private val _uiState = MutableStateFlow(CalendarUiState.Init)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    private var petDataFlowMap: Map<Int, Flow<List<CalendarUiState.Date>>>? = null
    private var currentPetList = petDataFlowMap?.get(0)


    init {
        viewModelScope.launch {
            Log.d(MainActivity.CALENDAR_LOG_TAG, "Before calling getCalendarWithMood")
            petDataFlowMap = getCalendarWithMood(_uiState.value.yearMonth)
            Log.d(MainActivity.CALENDAR_LOG_TAG, "After calling getCalendarWithMood: $petDataFlowMap")
            if (currentPetList == null) {
                Log.e(MainActivity.CALENDAR_LOG_TAG, "currentPetList is null")
            }
            currentPetList
                ?.flowOn(Dispatchers.IO)
                ?.collect { newDates ->
                    Log.d(MainActivity.CALENDAR_LOG_TAG, "Collected dates: $newDates")
                    _uiState.update { currentState ->
                        currentState.copy(dates = newDates)
                    }
                }
        }
    }

    fun toNextMonth(nextMonth: YearMonth) {
        viewModelScope.launch {
            petDataFlowMap = getCalendarWithMood(nextMonth)
            currentPetList = petDataFlowMap?.get(petIdLD.value)
            currentPetList?.collect { newDates ->
                _uiState.update { currentState ->
                    currentState.copy(
                        yearMonth = nextMonth,
                        dates = newDates
                    )
                }
            }
        }
    }

    fun toPreviousMonth(prevMonth: YearMonth) {
        viewModelScope.launch {
            petDataFlowMap = getCalendarWithMood(prevMonth)
            currentPetList = petDataFlowMap?.get(petIdLD.value)
            currentPetList?.collect { newDates ->
                _uiState.update { currentState ->
                    currentState.copy(
                        yearMonth = prevMonth,
                        dates = newDates
                    )
                }
            }
        }
    }

    fun onChangePet(petId: Int) {
        petIdLD.value = petId
    }

    //TODO store data in shared pref
    fun getPetsList(): List<Pet> {
        return listOf(
            Pet(0,"Ellie"),
            Pet(1,"Hina"),
        )
    }

    //TODO retrieve data from db
    fun getDayAttributesFood(): List<DayAttribute> {
        return listOf(
            DayAttribute(Icons.AutoMirrored.Default.Send, "food 1"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "food 2"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "food 3"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "food 4"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "food 5"),
        )
    }
    fun getDayAttributesHealth(): List<DayAttribute> {
        return listOf(
            DayAttribute(Icons.AutoMirrored.Default.Send, "health 1"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "health 2"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "health 3"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "health 4"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "health 5"),
        )
    }
    fun getDayAttributesEvents(): List<DayAttribute> {
        return listOf(
            DayAttribute(Icons.AutoMirrored.Default.Send, "events 1"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "events 2"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "events 3"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "events 4"),
            DayAttribute(Icons.AutoMirrored.Default.Send, "events 5"),
        )
    }

    fun addOrEditDayItem(selectedDayInfo: DayItemInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(MainActivity.CALENDAR_LOG_TAG, "Adding new item: $selectedDayInfo")
            addDayItemUseCase(selectedDayInfo)
        }
    }
}