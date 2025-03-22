package com.ideasapp.petemotions.presentation.viewModels

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.domain.entity.calendar.Pet.Companion.flattenToList
import com.ideasapp.petemotions.domain.use_case.calendar.AddDayItemUseCase
import com.ideasapp.petemotions.domain.use_case.calendar.AddPetUseCase
import com.ideasapp.petemotions.domain.use_case.calendar.GetCalendarUseCase
import com.ideasapp.petemotions.domain.use_case.calendar.GetMoodForPetUseCase
import com.ideasapp.petemotions.domain.use_case.calendar.GetPetsListUseCase
import com.ideasapp.petemotions.presentation.activity.MainActivity
import com.ideasapp.petemotions.presentation.activity.MainActivity.Companion.CALENDAR_LOG_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendar: GetCalendarUseCase,
    private val getMoodForPet : GetMoodForPetUseCase,
    private val addDayItemUseCase: AddDayItemUseCase,
    private val getPetsListUseCase : GetPetsListUseCase,
    private val addPetUseCase : AddPetUseCase
)  : ViewModel() {

    //TODO REFACTOR, DELETE UNNECESSARY

    private val petIdLD: MutableLiveData<Int> = MutableLiveData(0)

    private val _allDatesState = MutableStateFlow<List<CalendarUiState.Date>>(emptyList())

    private val _petDatesState = MutableStateFlow<List<CalendarUiState.Date>>(emptyList())

    private val _uiState = MutableStateFlow(CalendarUiState.Init)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    private val _petsList = MutableStateFlow<List<Pet>>(emptyList())
    val petsList = _petsList.asStateFlow()

    init {
        collectAllDates()
        collectForPetDates()
        viewModelScope.launch {
            collectPetList()
        }
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
                    collectForPetDates()
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

    //TODO use in onboarding and in profile
    suspend fun addPet(pet: Pet) {
        val currentList = _petsList.value
        val updatedList = currentList + pet
        _petsList.value = updatedList
        addPetUseCase(updatedList)
    }
    private suspend  fun collectPetList() {
        val flowOfLists: Flow<List<Pet>> = getPetsListUseCase()
        flowOfLists.collect { list ->
            _petsList.value = list
            Log.d(CALENDAR_LOG_TAG, "pets list: $list")
        }
    }
    private fun collectForPetDates() {
        petIdLD.asFlow().distinctUntilChanged().flatMapLatest {petId->
            getMoodForPet(_uiState.value.yearMonth, petId)
        }.flowOn(Dispatchers.IO).onEach {petDates->
            _petDatesState.value = petDates
            _uiState.update {currentState->
                currentState.copy(dates = petDates)
            }
        }.launchIn(viewModelScope)
    }
    private fun collectAllDates() {
        viewModelScope.launch {
            getCalendar(_uiState.value.yearMonth).flowOn(Dispatchers.IO).collect {newDates->
                _allDatesState.value = newDates
                _uiState.update {currentState->
                    currentState.copy(dates = newDates)
                }
            }
        }
    }


    //TODO retrieve data from db
    fun getDayAttributesFood(): List<DayAttribute> {
        return listOf(
            DayAttribute(
                Icons.Default.Menu,
                title = "fsfsf0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_FOOD
            ),
            DayAttribute(
                Icons.Default.Menu,
                title = "fsfsf0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_FOOD
            ),
            DayAttribute(
                Icons.Default.Menu,
                title = "fsfsf0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_FOOD
            ),
            DayAttribute(
                Icons.Default.Menu,
                title = "fsfsf0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_FOOD
            ),
            DayAttribute(
                Icons.Default.Menu,
                title = "fsfsf0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_FOOD
            ),DayAttribute(
                Icons.Default.Menu,
                title = "fsfsf0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_FOOD
            )
        )
    }
    fun getDayAttributesHealth(): List<DayAttribute> {
        return listOf(
            DayAttribute(
                Icons.Default.Menu,
                title = "fsfsf0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_HEALTH
            ),
            DayAttribute(
                Icons.Default.Menu,
                title = "fsfsf0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_HEALTH
            ),
            DayAttribute(
                Icons.Default.Menu,
                title = "fsfsf0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_HEALTH
            ),
            DayAttribute(
                Icons.Default.Menu,
                title = "fsfs0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_HEALTH
            ),
            DayAttribute(
                Icons.Default.Menu,
                title = "fssf0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_HEALTH
            ),DayAttribute(
                Icons.Default.Menu,
                title = "f0",
                id = 0,
                type = DayAttribute.ATTRIBUTE_TYPE_HEALTH
            )
        )
    }
    fun getDayAttributesEvents(): List<DayAttribute> {
        return listOf()
    }

    fun addOrEditDayItem(selectedDayInfo: DayItemInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(MainActivity.CALENDAR_LOG_TAG, "Adding new item: $selectedDayInfo")
            addDayItemUseCase(selectedDayInfo)
        }
    }
}