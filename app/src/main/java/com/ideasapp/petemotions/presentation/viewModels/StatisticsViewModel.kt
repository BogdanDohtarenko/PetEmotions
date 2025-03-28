package com.ideasapp.petemotions.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.domain.use_case.statistics.GetMoodPortionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getMoodPortion: GetMoodPortionUseCase,
): ViewModel() {

    private val _moodPortion: MutableLiveData<MoodPortion?> = MutableLiveData(null)
    val moodPortion: LiveData<MoodPortion?> = _moodPortion

    fun getMoodPortionData(petId: Int) {
        val moodPortion: Deferred<MoodPortion> = viewModelScope.async(Dispatchers.Default) {
            getMoodPortion(petId)
        }
        viewModelScope.launch {
            _moodPortion.value = moodPortion.await()
        }
    }

}