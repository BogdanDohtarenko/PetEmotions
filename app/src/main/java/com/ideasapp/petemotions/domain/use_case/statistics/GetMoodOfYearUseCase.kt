package com.ideasapp.petemotions.domain.use_case.statistics

import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.domain.repositories.StatisticsRepository
import javax.inject.Inject

class GetMoodOfYearUseCase @Inject constructor(
    private val repository:StatisticsRepository,
) {
    suspend operator fun invoke(petId: Int): MoodOfYear {
        return repository.getMoodOfYear(petId)
    }
}