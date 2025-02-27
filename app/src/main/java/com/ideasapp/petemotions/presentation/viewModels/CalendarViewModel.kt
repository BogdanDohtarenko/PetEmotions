package com.ideasapp.petemotions.presentation.viewModels

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.data.repositories_impl.CalendarRepositoryImpl
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
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
import java.time.LocalDate
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

    fun addNewItem(selectedDayInfo: DayItemInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO AMEND
            val newSelectedDayInfo =
                DayItemInfo(date = LocalDate.of(2025, 2, 28).toEpochDay(), mood = "T")
            Log.d("Calendar", "Adding new item: $newSelectedDayInfo")
            addDayItemUseCase(newSelectedDayInfo)
        }
    }
}