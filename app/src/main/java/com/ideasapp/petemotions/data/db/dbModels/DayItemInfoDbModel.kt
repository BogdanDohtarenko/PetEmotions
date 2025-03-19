package com.ideasapp.petemotions.data.db.dbModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo.Companion.EMPTY

@Entity(tableName = "DayInfo")
data class DayItemInfoDbModel(
    @PrimaryKey(autoGenerate = false) val date: Long,
    @ColumnInfo(name = "petId") val petId: Int,
    @ColumnInfo(name = "mood") val mood: Int? = null
)