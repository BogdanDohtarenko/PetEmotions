package com.ideasapp.petemotions.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.domain.use_case.timetable.AddTimetableItemUseCase
import com.ideasapp.petemotions.domain.use_case.timetable.DeleteTimetableItemUseCase
import com.ideasapp.petemotions.domain.use_case.timetable.GetTimetableListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val getTimetableListUseCase: GetTimetableListUseCase,
    private val addTimetableItemUseCase: AddTimetableItemUseCase,
    private val deleteTimetableItemUseCase: DeleteTimetableItemUseCase,
) : ViewModel() {

    private val _timetableItems = MutableStateFlow<PagingData<TimetableItem>>(PagingData.empty())
    val timetableFlow: StateFlow<PagingData<TimetableItem>> = _timetableItems.asStateFlow()

    init {
        loadTimetableItems()
    }

    private fun loadTimetableItems() {
        viewModelScope.launch {
            getTimetableListUseCase().cachedIn(viewModelScope).collect { pagingData ->
                _timetableItems.value = pagingData
            }
        }
    }

    fun addItem(newItem: TimetableItem) {
        viewModelScope.launch(Dispatchers.IO) {
            addTimetableItemUseCase(newItem)
            loadTimetableItems()
        }
    }

    fun deleteItem(oldItem: TimetableItem) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTimetableItemUseCase(oldItem)
            loadTimetableItems()
        }
    }
}

