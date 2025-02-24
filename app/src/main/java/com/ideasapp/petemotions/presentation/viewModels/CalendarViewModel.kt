package com.ideasapp.petemotions.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.data.repositories_impl.CalendarRepositoryImpl
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.presentation.ui.screens.calendar.CalendarDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth

class CalendarViewModel : ViewModel() {

    // TODO: Utilize DI (Hilt)
    private val repository by lazy {
        CalendarRepositoryImpl
    }

    private val _uiState=MutableStateFlow(CalendarUiState.Init)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState: CalendarUiState ->
                val currentMonth = currentState.yearMonth
                currentState.copy(dates = repository.getSystemCalendar(currentMonth))
            }
        }
    }

    fun toNextMonth( nextMonth:YearMonth ) {
        viewModelScope.launch {
            _uiState.update { currentState: CalendarUiState ->
                currentState.copy(
                    yearMonth = nextMonth,
                    dates = repository.getSystemCalendar(nextMonth)
                )
            }
        }
    }

    fun toPreviousMonth(prevMonth: YearMonth) {
        viewModelScope.launch {
            _uiState.update { currentState: CalendarUiState ->
                currentState.copy(
                    yearMonth = prevMonth,
                    dates = repository.getSystemCalendar(prevMonth)
                )
            }
        }
    }
}