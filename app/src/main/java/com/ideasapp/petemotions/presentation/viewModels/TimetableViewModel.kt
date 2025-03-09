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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val getTimetableListUseCase : GetTimetableListUseCase,
    private val addTimetableItemUseCase : AddTimetableItemUseCase,
    private val deleteTimetableItemUseCase : DeleteTimetableItemUseCase,
): ViewModel() {
    //TODO
    //The Paging Library makes it easier for you to load data gradually and gracefully within your app's
    fun getTimetableFlow(): Flow<PagingData<TimetableItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { getTimetableListUseCase() }
        ).flow.cachedIn(viewModelScope)
    }

    fun addItem(newItem : TimetableItem) {
        viewModelScope.launch(Dispatchers.IO) {
            addTimetableItemUseCase(newItem)
        }
    }

    fun deleteItem(oldItem : TimetableItem) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTimetableItemUseCase(oldItem)
        }
    }

}

