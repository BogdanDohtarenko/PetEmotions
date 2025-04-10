package com.ideasapp.petemotions.data.repositories_impl

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.data.db.dao.DayAttributesDao
import com.ideasapp.petemotions.data.db.dbModels.DayAttributesDbModel
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

        val attributesList = mutableListOf<String>()
        yearData.forEach { dayInfoItem ->
            dayInfoItem.attributeNames.forEach { name ->
                attributesList.add(name)
            }
        }
        val totalAttributes = attributesList.size

        val attributeCounts = attributesList.groupingBy { it }.eachCount()

        val attributePercentages = attributeCounts.mapValues { (attribute, count) ->
            (count.toDouble() / totalAttributes) * 100
        }
        val uniqueAttributePercentages = attributePercentages.toMap()

        uniqueAttributePercentages.forEach { (attribute, percentage) ->
            Log.d("AttributeDiagram", "Attribute: $attribute, Percentage: %.2f%%".format(percentage))
        }

        return listOf(
            ChartModel(value = 45f, color = Color.Black, name = "Good walk"),
            ChartModel(value = 5f, color = Color.Gray, name = "Boring walk"),
            ChartModel(value = 20f, color = Color.Green, name = "Dog friends"),
            ChartModel(value = 30f, color = Color.Red, name = "Training"),
        )
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
}