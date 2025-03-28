package com.ideasapp.petemotions.domain.use_case.statistics

import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.domain.repositories.StatisticsRepository
import javax.inject.Inject

class GetMoodPortionUseCase @Inject constructor(
    private val repository: StatisticsRepository,
) {
    operator fun invoke(): MoodPortion {
        return repository.getMoodPortion()
    }
}