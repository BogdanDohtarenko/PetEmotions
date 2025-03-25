package com.ideasapp.petemotions.data.db.dbModels

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DayAttributes")
data class DayAttributesDbModel(
    @ColumnInfo(name = "imageVector") val imageVector: ImageVector,
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey(autoGenerate = true)  val id: Int,
    @ColumnInfo(name = "type") val type: String,
)