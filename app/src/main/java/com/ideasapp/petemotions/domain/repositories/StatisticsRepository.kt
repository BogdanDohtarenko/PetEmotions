package com.ideasapp.petemotions.domain.repositories

import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion

interface StatisticsRepository {
    suspend fun getMoodPortion(petId: Int): MoodPortion
    suspend fun getMoodOfYear(petId: Int):MoodOfYear
}