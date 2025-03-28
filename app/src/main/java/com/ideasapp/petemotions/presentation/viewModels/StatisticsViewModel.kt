package com.ideasapp.petemotions.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.ideasapp.petemotions.domain.use_case.statistics.GetMoodPortionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getMoodPortion: GetMoodPortionUseCase,
): ViewModel() {

}