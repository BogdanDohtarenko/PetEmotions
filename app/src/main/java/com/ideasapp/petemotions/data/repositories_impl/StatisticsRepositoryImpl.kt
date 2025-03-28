package com.ideasapp.petemotions.data.repositories_impl

import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.domain.repositories.StatisticsRepository
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val calendarListDao:CalendarListDao,
): StatisticsRepository {

    override suspend fun getMoodPortion(petId: Int): MoodPortion {
        calendarListDao.getDayInfoList(petId)
        val moodPortion = MoodPortion(badPercents = petId, goodPercents = 50, normalPercents = 40)
        return moodPortion
    }

}