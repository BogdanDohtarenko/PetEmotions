package com.ideasapp.petemotions.data.repositories_impl

import android.util.Log
import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.domain.repositories.StatisticsRepository
import java.time.Instant
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

    override suspend fun getMoodOfYear(petId: Int, year: Int):MoodOfYear {

        val dayInfoList = calendarListDao.getDayInfoList(petId)

        //take only our year
        val filteredByYearDays = dayInfoList.filter { item ->
            val localDate = Instant.ofEpochMilli(item.date)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            localDate.year == year
        }

        Log.d("Statistics", "year: $year")

        var januaryDataSum: Double = 0.0
        var februaryDataSum: Double = 0.0
        var marchDataSum: Double = 0.0
        var aprilDataSum: Double = 0.0
        var mayDataSum: Double = 0.0
        var juneDataSum: Double = 0.0
        var julyDataSum: Double = 0.0
        var augustDataSum: Double = 0.0
        var septemberDataSum: Double = 0.0
        var octoberDataSum: Double = 0.0
        var novemberDataSum: Double = 0.0
        var decemberDataSum: Double = 0.0

        var januaryDataCount = 0.0
        var februaryDataCount = 0.0
        var marchDataCount = 0.0
        var aprilDataCount = 0.0
        var mayDataCount = 0.0
        var juneDataCount = 0.0
        var julyDataCount = 0.0
        var augustDataCount = 0.0
        var septemberDataCount = 0.0
        var octoberDataCount = 0.0
        var novemberDataCount = 0.0
        var decemberDataCount =0.0

        filteredByYearDays.forEach { item ->
            val localDate = Instant.ofEpochMilli(item.date)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val moodValue = when(item.mood) {
                1 -> 99.0
                2 -> 66.0
                3 -> 33.0
                null -> 0.0
                else -> throw RuntimeException("Unknown item.mood in statistics")
            }
            if (moodValue != 0.0) {
                when (localDate.monthValue) {
                    1 -> {
                        januaryDataCount++
                        januaryDataSum += moodValue
                    }
                    2 -> {
                        februaryDataCount++
                        februaryDataSum += moodValue
                    }
                    3 -> {
                        marchDataCount++
                        marchDataSum += moodValue
                    }
                    4 -> {
                        aprilDataCount++
                        aprilDataSum += moodValue
                    }
                    5 -> {
                        mayDataCount++
                        mayDataSum += moodValue
                    }
                    6 -> {
                        juneDataCount++
                        juneDataSum += moodValue
                    }
                    7 -> {
                        julyDataCount++
                        julyDataSum += moodValue
                    }
                    8 -> {
                        augustDataCount++
                        augustDataSum += moodValue
                    }
                    9 -> {
                        septemberDataCount++
                        septemberDataSum += moodValue
                    }
                    10 -> {
                        octoberDataCount++
                        octoberDataSum += moodValue
                    }
                    11 -> {
                        novemberDataCount++
                        novemberDataSum += moodValue
                    }
                    12 -> {
                        decemberDataCount++
                        decemberDataSum += moodValue
                    }
                }
            }
        }
//TODO DELENIE NA NOL
        val januaryDataAverage = (januaryDataSum/januaryDataCount).toInt()
        val februaryDataAverage = (februaryDataSum/februaryDataCount).toInt()
        val marchDataAverage = (marchDataSum/marchDataCount).toInt()
        val aprilDataAverage = (aprilDataSum/aprilDataCount).toInt()
        val mayDataAverage = (mayDataSum/mayDataCount).toInt()
        val juneDataAverage = (juneDataSum/juneDataCount).toInt()
        val julyDataAverage = (julyDataSum/julyDataCount).toInt()
        val augustDataAverage = (augustDataSum/augustDataCount).toInt()
        val septemberDataAverage = (septemberDataSum/septemberDataCount).toInt()
        val octoberDataAverage = (octoberDataSum/octoberDataCount).toInt()
        val novemberDataAverage = (novemberDataSum/novemberDataCount).toInt()
        val decemberDataAverage = (decemberDataSum/decemberDataCount).toInt()

        Log.d("Statistics", "January: Count = $januaryDataCount, Sum = $januaryDataSum")
        Log.d("Statistics", "February: Count = $februaryDataCount, Sum = $februaryDataSum")
        Log.d("Statistics", "March: Count = $marchDataCount, Sum = $marchDataSum")
        Log.d("Statistics", "April: Count = $aprilDataCount, Sum = $aprilDataSum")
        Log.d("Statistics", "May: Count = $mayDataCount, Sum = $mayDataSum")
        Log.d("Statistics", "June: Count = $juneDataCount, Sum = $juneDataSum")
        Log.d("Statistics", "July: Count = $julyDataCount, Sum = $julyDataSum")
        Log.d("Statistics", "August: Count = $augustDataCount, Sum = $augustDataSum")
        Log.d("Statistics", "September: Count = $septemberDataCount, Sum = $septemberDataSum")
        Log.d("Statistics", "October: Count = $octoberDataCount, Sum = $octoberDataSum")
        Log.d("Statistics", "November: Count = $novemberDataCount, Sum = $novemberDataSum")
        Log.d("Statistics", "December: Count = $decemberDataCount, Sum = $decemberDataSum")

        return MoodOfYear(
            januaryData = if (januaryDataCount == 0.0) null else januaryDataAverage,
            februaryData = if (februaryDataCount == 0.0)  null else februaryDataAverage,
            marchData = if (marchDataCount ==0.0) null else marchDataAverage,
            aprilData = if (aprilDataCount == 0.0) null else aprilDataAverage,
            mayData = if (mayDataCount ==0.0) null else mayDataAverage,
            juneData = if (juneDataCount == 0.0) null else juneDataAverage,
            julyData = if (julyDataCount == 0.0) null else julyDataAverage,
            augustData = if (augustDataCount == 0.0) null else augustDataAverage,
            septemberData = if (septemberDataCount == 0.0) null else septemberDataAverage,
            octoberData = if (octoberDataCount == 0.0) null else octoberDataAverage,
            novemberData = if (novemberDataCount == 0.0) null else novemberDataAverage,
            decemberData = if (decemberDataCount == 0.0) null else decemberDataAverage
        )
    }
}