package com.ideasapp.petemotions.data.db.dbModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo.Companion.EMPTY

@Entity(tableName = "DayInfo", primaryKeys= [ "date", "petId" ])
data class DayItemInfoDbModel(
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "petId") val petId: Int,
    @ColumnInfo(name = "mood") val mood: Int? = null,
    @ColumnInfo(name = "attributeNames") val attributeNames: List<String> = listOf<String>()
)