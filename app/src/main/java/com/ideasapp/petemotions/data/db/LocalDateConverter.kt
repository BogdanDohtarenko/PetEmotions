package com.ideasapp.petemotions.data.db

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ImageVectorConverter {

    @TypeConverter
    fun fromImageVector(date: ImageVector?): String? {
        return
    }

    @TypeConverter
    fun toImageVector(dateString: String?): ImageVector? {
        return
    }
}