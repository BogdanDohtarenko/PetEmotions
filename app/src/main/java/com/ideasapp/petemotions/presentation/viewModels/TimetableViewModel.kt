package com.ideasapp.petemotions.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(): ViewModel() {
    //TODO
    //The Paging Library makes it easier for you to load data gradually and gracefully within your app's
    fun getTimetableFlow(): Flow<PagingData<TimetableItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TimetablePagingSource() }
        ).flow.cachedIn(viewModelScope)
    }

}

