package com.ideasapp.petemotions.domain.repositories

import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion

interface StatisticsRepository {
    fun getMoodPortion(): MoodPortion
}