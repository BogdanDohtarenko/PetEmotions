package com.ideasapp.petemotions.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.data.repositories_impl.CalendarRepositoryImpl
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.domain.use_case.calendar.GetCalendarWithMood
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

class CalendarViewModel /*@Inject constructor(repository: CalendarRepositoryImpl)*/ : ViewModel() {

    // TODO: Utilize DI (Hilt)
    private val repository = CalendarRepositoryImpl
    private val getCalendarWithMood = GetCalendarWithMood(repository)

    private val _uiState=MutableStateFlow(CalendarUiState.Init)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        //launch get month with mood in io thread
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState: CalendarUiState ->
                val currentMonth = currentState.yearMonth
                currentState.copy(dates = getCalendarWithMood(currentMonth))
            }
        }
    }

    fun toNextMonth( nextMonth:YearMonth ) {
        viewModelScope.launch {
            _uiState.update { currentState: CalendarUiState ->
                currentState.copy(
                    yearMonth = nextMonth,
                    dates = getCalendarWithMood(nextMonth)
                )
            }
        }
    }

    fun toPreviousMonth(prevMonth: YearMonth) {
        viewModelScope.launch {
            _uiState.update { currentState: CalendarUiState ->
                currentState.copy(
                    yearMonth = prevMonth,
                    dates = getCalendarWithMood(prevMonth)
                )
            }
        }
    }
}