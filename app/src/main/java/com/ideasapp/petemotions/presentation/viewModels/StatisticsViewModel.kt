package com.ideasapp.petemotions.presentation.viewModels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ideasapp.petemotions.domain.entity.stastistics.ChartModel
import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.domain.use_case.statistics.GetAttributesOfYearUseCase
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
    private val getAttributesOfYearUseCase: GetAttributesOfYearUseCase,
): ViewModel() {

    private val _moodPortion: MutableLiveData<MoodPortion?> = MutableLiveData(null)
    val moodPortion: LiveData<MoodPortion?> = _moodPortion

    private val _moodOfYear: MutableLiveData<MoodOfYear?> = MutableLiveData(null)
    val moodOfYear: LiveData<MoodOfYear?> = _moodOfYear

    private val _charts: MutableLiveData<List<ChartModel>?> = MutableLiveData(null)
    val charts: LiveData<List<ChartModel>?> = _charts

    val possibleYearsList = listOf(2025, 2026, 2027)

    fun getMoodPortionData(petId: Int) {
        viewModelScope.launch {
            val moodPortion = withContext(Dispatchers.Default) {
                getMoodPortion(petId)
            }
            _moodPortion.value = moodPortion
        }
    }

    fun getMoodOfYearByMonth(petId: Int, selectedYearIndex: Int) {
        viewModelScope.launch {
            val selectedYear = possibleYearsList[selectedYearIndex]
            val moodOfYear = withContext(Dispatchers.Default) {
                getMoodOfYearUseCase(petId, selectedYear)
            }
            _moodOfYear.value = moodOfYear
        }
    }

    fun getCharts(petId: Int, selectedYearIndex: Int) {
        viewModelScope.launch {
            val selectedYear = possibleYearsList[selectedYearIndex]
            val charts = withContext(Dispatchers.Default) {
                getAttributesOfYearUseCase(petId, selectedYear)
                listOf( //TODO remove
                    ChartModel(value = 45f, color = Color.Black, name = "Good walk"),
                    ChartModel(value = 5f, color = Color.Gray, name = "Boring walk"),
                    ChartModel(value = 20f, color = Color.Green, name = "Dog friends"),
                    ChartModel(value = 30f, color = Color.Red, name = "Training"),
                )
            }
            _charts.value = charts
        }
    }

}