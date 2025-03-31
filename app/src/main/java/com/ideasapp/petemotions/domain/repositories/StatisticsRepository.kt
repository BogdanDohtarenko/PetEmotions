package com.ideasapp.petemotions.domain.repositories

import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import java.time.Year

interface StatisticsRepository {
    suspend fun getMoodPortion(petId: Int): MoodPortion
    suspend fun getMoodOfYear(petId: Int, year: Int):MoodOfYear
}