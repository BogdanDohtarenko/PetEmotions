package com.ideasapp.petemotions.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo.Companion.EMPTY
import java.time.LocalDate

@Entity(tableName = "DayInfo")
data class DayItemInfoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val date: LocalDate,
    val mood: String = EMPTY
)