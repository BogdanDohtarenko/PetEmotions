package com.ideasapp.petemotions.data.db.dbModels

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DayAttributes")
data class DayAttributesDbModel(
    @ColumnInfo(name = "imageVectorResource") val imageVectorResource: Int,
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey(autoGenerate = true)  val id: Int,
    @ColumnInfo(name = "type") val type: String,
)