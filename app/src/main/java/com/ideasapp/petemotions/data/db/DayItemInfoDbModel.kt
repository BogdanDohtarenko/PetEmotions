package com.ideasapp.petemotions.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo.Companion.EMPTY
import java.time.LocalDate

@Entity(tableName = "DayInfo")
data class DayItemInfoDbModel(
    @PrimaryKey(autoGenerate = false) val date: Long,
    @ColumnInfo(name = "mood") val mood: String = EMPTY
)