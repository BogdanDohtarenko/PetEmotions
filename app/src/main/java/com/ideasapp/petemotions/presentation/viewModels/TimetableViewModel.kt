package com.ideasapp.petemotions.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(): ViewModel() {
    //TODO
    fun getTimetableList(): List<TimetableItem> {
        val list = mutableListOf<TimetableItem>()
        for(i in 1..100) {
            list.add(TimetableItem(description = "$i"))
        }
        return list
    }

}