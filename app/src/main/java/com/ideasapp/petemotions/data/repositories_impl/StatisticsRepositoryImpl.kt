package com.ideasapp.petemotions.data.repositories_impl

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.data.db.dbModels.DayItemInfoDbModel
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.stastistics.ChartModel
import com.ideasapp.petemotions.domain.entity.stastistics.MoodOfYear
import com.ideasapp.petemotions.domain.entity.stastistics.MoodPortion
import com.ideasapp.petemotions.domain.repositories.StatisticsRepository
import java.time.LocalDate
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val calendarListDao: CalendarListDao,
): StatisticsRepository {

    override suspend fun getAttributesByYear(petId:Int, year:Int): List<ChartModel> {

        val dayInfoList: List<DayItemInfoDbModel> = calendarListDao.getDayInfoList(petId)
        val yearData = dayInfoList.filter { item ->
            val localDate = LocalDate.ofEpochDay(item.date)
            localDate.year == year
        }

        val attributeCounts = mutableMapOf<String, Int>()
        yearData.forEach { dayInfoItem ->
            dayInfoItem.attributeNames.forEach { name ->
                attributeCounts[name] = attributeCounts.getOrDefault(name, 0) + 1
            }
        }

        val topAttributes = attributeCounts.toList()
            .sortedByDescending { it.second }
            .take(8)

        val totalCount = topAttributes.sumOf { it.second }

        val topAttributesList =  topAttributes.map { (attribute, count) ->
            val percentage = if (totalCount > 0) {
                (count.toDouble() / totalCount) * 100
            } else {
                0.0
            }
            attribute to percentage
        }

        val chartModelsList = convertToPercentageChartModels(topAttributesList)
        Log.d("AttributeDiagram", "$chartModelsList")

        return chartModelsList
    }

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
                val localDate = LocalDate.ofEpochDay(item.date)

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

    private fun convertToPercentageChartModels(originalList: List<Pair<String, Double>>): List<ChartModel> {
        val colors = listOf(
            Color.Green.copy(alpha = 1f).copy(red = 0.5f, green = 0.7f, blue = 0.5f),  // Softer Green
            Color.Red.copy(alpha = 1f).copy(red = 0.8f, green = 0.4f, blue = 0.4f),    // Softer Red
            Color.Magenta.copy(alpha = 1f).copy(red = 0.8f, green = 0.4f, blue = 0.8f), // Softer Magenta
            Color.Blue.copy(alpha = 1f).copy(red = 0.4f, green = 0.4f, blue = 0.6f),   // Softer Blue
            Color.Gray.copy(alpha = 1f).copy(red = 0.3f, green = 0.3f, blue = 0.3f),   // Dark Gray (replacement for Black)                                           // Black remains the same
            Color.Cyan.copy(alpha = 1f).copy(red = 0.4f, green = 0.6f, blue = 0.6f), // Softer Cyan
            Color.Yellow.copy(alpha = 1f).copy(red = 0.8f, green = 0.8f, blue = 0.4f), // Softer Yellow
            Color.Gray.copy(alpha = 1f).copy(red = 0.6f, green = 0.6f, blue = 0.6f)   // Softer Gray
        )

        var count = 0

        return originalList.map { (name, value) ->
            ChartModel(value = value.toFloat(), color = colors[count++], name = name)
        }
    }
}