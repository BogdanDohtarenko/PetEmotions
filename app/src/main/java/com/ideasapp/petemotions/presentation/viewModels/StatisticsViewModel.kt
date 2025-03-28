package com.ideasapp.petemotions.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.domain.use_case.statistics.GetMoodOfYearUseCase
import com.ideasapp.petemotions.domain.use_case.statistics.GetMoodPortionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getMoodPortion: GetMoodPortionUseCase,
    private val getMoodOfYearUseCase: GetMoodOfYearUseCase,
): ViewModel() {

    private val _moodPortion: MutableLiveData<MoodPortion?> = MutableLiveData(null)
    val moodPortion: LiveData<MoodPortion?> = _moodPortion

    private val _moodOfYear: MutableLiveData<MoodOfYear?> = MutableLiveData(null)
    val moodOfYear: LiveData<MoodOfYear?> = _moodOfYear

    fun getMoodPortionData(petId: Int) {
        viewModelScope.launch {
            val moodPortion = withContext(Dispatchers.Default) {
                getMoodPortion(petId)
            }
            _moodPortion.value = moodPortion
        }
    }

    fun getMoodOfYearByMonth(petId: Int) {
        viewModelScope.launch {
            val moodOfYear = withContext(Dispatchers.Default) {
                getMoodOfYearUseCase(petId)
            }
            _moodOfYear.value = moodOfYear
        }
    }

}