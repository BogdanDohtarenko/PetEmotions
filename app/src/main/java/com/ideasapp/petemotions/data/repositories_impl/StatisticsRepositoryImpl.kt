package com.ideasapp.petemotions.data.repositories_impl

import android.util.Log
import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.domain.repositories.StatisticsRepository
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val calendarListDao: CalendarListDao,
): StatisticsRepository {

    override suspend fun getMoodPortion(petId: Int): MoodPortion {
        val dayInfoList = calendarListDao.getDayInfoList(petId)

        //counters
        var goodDaysCount = 0.0
        var normalDaysCount = 0.0
        var badDaysCount = 0.0

        //count each mood frequency
        dayInfoList.forEach { item ->
            when(item.mood) {
                DayItemInfo.GOOD_MOOD -> goodDaysCount++
                DayItemInfo.NORMAL_MOOD -> normalDaysCount++
                DayItemInfo.BAD_MOOD -> badDaysCount++
            }
        }
        //percent values
        val allDayCount = goodDaysCount + normalDaysCount + badDaysCount
        val goodDaysPercent: Double = goodDaysCount/allDayCount * 100
        val normalDaysPercent: Double = normalDaysCount/allDayCount * 100
        val badDaysPercent: Double = badDaysCount/allDayCount * 100

        //create MoodPortion object
        return MoodPortion(
            allDaysCount = allDayCount.toInt(),
            badPercents = badDaysPercent.toInt(),
            goodPercents = goodDaysPercent.toInt(),
            normalPercents = normalDaysPercent.toInt()
        )
    }

    override suspend fun getMoodOfYear(petId: Int, year: Int): MoodOfYear {
        val dayInfoList = calendarListDao.getDayInfoList(petId)
        val monthlyData = dayInfoList
            .mapNotNull { item ->
                val localDate = LocalDate.ofEpochDay(item.date.toLong())

                if (localDate.year == year) {
                    val moodValue = when (item.mood) {
                        1 -> 99.0
                        2 -> 66.0
                        3 -> 33.0
                        else -> null
                    }
                    if (moodValue != null) {
                        Pair(localDate.monthValue, moodValue)
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { (_, values) ->
                values.average().toInt()
            }

        return MoodOfYear(
            januaryData = monthlyData[1],
            februaryData = monthlyData[2],
            marchData = monthlyData[3],
            aprilData = monthlyData[4],
            mayData = monthlyData[5],
            juneData = monthlyData[6],
            julyData = monthlyData[7],
            augustData = monthlyData[8],
            septemberData = monthlyData[9],
            octoberData = monthlyData[10],
            novemberData = monthlyData[11],
            decemberData = monthlyData[12]
        )
    }
}