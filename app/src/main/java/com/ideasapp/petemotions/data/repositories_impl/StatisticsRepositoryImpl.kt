package com.ideasapp.petemotions.data.repositories_impl

import android.util.Log
import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.data.db.dbModels.DayItemInfoDbModel
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.domain.repositories.StatisticsRepository
import java.time.Year
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val calendarListDao: CalendarListDao,
): StatisticsRepository {

    override suspend fun getMoodPortion(petId: Int): MoodPortion {
        val dayInfoList = calendarListDao.getDayInfoList(petId)

        //counters
        var goodDaysCount: Double = 0.0
        var normalDaysCount: Double = 0.0
        var badDaysCount: Double = 0.0

        //count each mood frequency
        dayInfoList.forEach { item ->
            when(item.mood) {
                DayItemInfo.GOOD_MOOD -> goodDaysCount++
                DayItemInfo.NORMAL_MOOD -> normalDaysCount++
                DayItemInfo.BAD_MOOD -> badDaysCount++
            }
        }

        Log.d("Statistics", "goodDaysCount $goodDaysCount")
        Log.d("Statistics", "normalDaysCount $normalDaysCount")
        Log.d("Statistics", "badDaysCount $badDaysCount")

        //percent values
        val allDayCount = goodDaysCount + normalDaysCount + badDaysCount
        val goodDaysPercent: Double = goodDaysCount/allDayCount * 100
        val normalDaysPercent: Double = normalDaysCount/allDayCount * 100
        val badDaysPercent: Double = badDaysCount/allDayCount * 100

        Log.d("Statistics", "goodDaysPercent $goodDaysPercent")
        Log.d("Statistics", "normalDaysPercent $normalDaysPercent")
        Log.d("Statistics", "badDaysPercent $badDaysPercent")

        //create MoodPortion object
        return MoodPortion(
            allDaysCount = allDayCount.toInt(),
            badPercents = badDaysPercent.toInt(),
            goodPercents = goodDaysPercent.toInt(),
            normalPercents = normalDaysPercent.toInt()
        )
    }

    override suspend fun getMoodOfYear(petId:Int):MoodOfYear {
        val moodOfYear = MoodOfYear( //TODO amend
            year = Year.now(),
            januaryData = 60,
            februaryData = 70,
            marchData = 80,
            aprilData = 50,
            mayData = null,
            juneData = 100,
            julyData = 90,
            augustData = 70,
            septemberData = 80,
            octoberData = 90,
            novemberData = 90,
            decemberData = 100
        )
        return moodOfYear
    }
}