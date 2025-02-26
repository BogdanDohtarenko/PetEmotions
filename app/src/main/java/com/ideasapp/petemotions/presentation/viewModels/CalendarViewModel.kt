package com.ideasapp.petemotions.presentation.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.data.repositories_impl.CalendarRepositoryImpl
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.domain.use_case.calendar.AddDayItemUseCase
import com.ideasapp.petemotions.domain.use_case.calendar.GetCalendarWithMood
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

class CalendarViewModel(application: Application)
/*@Inject constructor(repository: CalendarRepositoryImpl)*/
    : AndroidViewModel(application) {

    // TODO: Utilize DI (Hilt)
    private val repository = CalendarRepositoryImpl(application)
    private val getCalendarWithMood = GetCalendarWithMood(repository)
    private val addDayItemUseCase = AddDayItemUseCase(repository)

    private val _uiState = MutableStateFlow(CalendarUiState.Init)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCalendarWithMood(_uiState.value.yearMonth)
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
            repository.getCalendarWithMood(nextMonth)
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
            repository.getCalendarWithMood(prevMonth)
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