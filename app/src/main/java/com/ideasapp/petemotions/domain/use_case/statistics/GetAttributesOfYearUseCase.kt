package com.ideasapp.petemotions.domain.use_case.statistics

import com.ideasapp.petemotions.domain.entity.stastistics.ChartModel
import com.ideasapp.petemotions.domain.repositories.StatisticsRepository
import javax.inject.Inject

class GetAttributesOfYearUseCase @Inject constructor(
    private val repository:StatisticsRepository,
) {
    suspend operator fun invoke(petId: Int, year: Int): List<ChartModel> {
        return repository.getAttributesByYear(petId, year)
    }
}