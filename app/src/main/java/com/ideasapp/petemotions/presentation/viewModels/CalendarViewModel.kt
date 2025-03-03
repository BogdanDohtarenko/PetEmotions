package com.ideasapp.petemotions.presentation.viewModels

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.use_case.calendar.AddDayItemUseCase
import com.ideasapp.petemotions.domain.use_case.calendar.GetCalendarWithMood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarWithMood: GetCalendarWithMood,
    private val addDayItemUseCase: AddDayItemUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarUiState.Init)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCalendarWithMood(_uiState.value.yearMonth)
                .flowOn(Dispatchers.IO)
                .collect { newDates ->
                    _uiState.update { currentState ->
                        currentState.copy(dates = newDates)
                    }
                }
        }
    }

    fun toNextMonth( nextMonth: YearMonth ) {
        viewModelScope.launch {
            getCalendarWithMood(nextMonth)
                .flowOn(Dispatchers.IO)
                .collect { newDates ->
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
            getCalendarWithMood(prevMonth)
                .flowOn(Dispatchers.IO)
                .collect { newDates ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            yearMonth = prevMonth,
                            dates = newDates
                        )
                    }
                }
        }
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
            Log.d("Calendar", "Adding new item: $selectedDayInfo")
            addDayItemUseCase(selectedDayInfo)
        }
    }
}