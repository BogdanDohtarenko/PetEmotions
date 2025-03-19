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
import com.ideasapp.petemotions.domain.use_case.calendar.GetCalendarUseCase
import com.ideasapp.petemotions.domain.use_case.calendar.GetMoodForPetUseCase
import com.ideasapp.petemotions.presentation.activity.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendar: GetCalendarUseCase,
    private val getMoodForPet : GetMoodForPetUseCase,
    private val addDayItemUseCase: AddDayItemUseCase
)  : ViewModel() {

    //TODO REFACTOR DELETE UNNECESSARY

    private val petIdLD: MutableLiveData<Int> = MutableLiveData(0)

    private val _allDatesState = MutableStateFlow<List<CalendarUiState.Date>>(emptyList())
    val allDatesState: StateFlow<List<CalendarUiState.Date>> = _allDatesState.asStateFlow()

    private val _petDatesState = MutableStateFlow<List<CalendarUiState.Date>>(emptyList())
    val petDatesState: StateFlow<List<CalendarUiState.Date>> = _petDatesState.asStateFlow()

    private val _uiState = MutableStateFlow(CalendarUiState.Init)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCalendar(_uiState.value.yearMonth)
                .flowOn(Dispatchers.IO)
                .collect { newDates ->
                    _allDatesState.value = newDates
                    _uiState.update { currentState ->
                        currentState.copy(dates = newDates)
                    }
                }
        }
        petIdLD.asFlow()
            .distinctUntilChanged()
            .flatMapLatest { petId ->
                getMoodForPet(_uiState.value.yearMonth, petId)
            }
            .flowOn(Dispatchers.IO)
            .onEach { petDates ->
                _petDatesState.value = petDates
                _uiState.update { currentState ->
                    currentState.copy(dates = petDates)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun updateDatesForMonth(yearMonth: YearMonth) {
        viewModelScope.launch {
            getCalendar(yearMonth)
                .collect { newDates ->
                    _allDatesState.value = newDates
                    _uiState.update { currentState ->
                        currentState.copy(
                            yearMonth = yearMonth,
                            dates = newDates
                        )
                    }
                    petIdLD.asFlow()
                        .distinctUntilChanged()
                        .flatMapLatest { petId ->
                            getMoodForPet(_uiState.value.yearMonth, petId)
                        }
                        .flowOn(Dispatchers.IO)
                        .onEach { petDates ->
                            _petDatesState.value = petDates
                            _uiState.update { currentState ->
                                currentState.copy(dates = petDates)
                            }
                        }
                        .launchIn(viewModelScope)
                }
        }
    }

    fun toNextMonth(nextMonth: YearMonth) {
        updateDatesForMonth(nextMonth)
    }

    fun toPreviousMonth(prevMonth: YearMonth) {
        updateDatesForMonth(prevMonth)
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