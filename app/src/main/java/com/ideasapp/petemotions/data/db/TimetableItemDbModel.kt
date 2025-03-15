package com.ideasapp.petemotions.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Timetable")
data class TimetableItemDbModel (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "dateTime") val dateTime: Long = System.currentTimeMillis()
) {}