package com.ideasapp.petemotions.data.db.converters

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.TypeConverter

class ImageVectorConverter {

    @TypeConverter
    fun fromImageVector(date: ImageVector?): String? {
        return date?.let { it.name.split(".")[1] }
    }

    @TypeConverter
    fun toImageVector(dateString: String?): ImageVector {
        val cl = Class.forName("androidx.compose.material.icons.filled.${dateString}Kt")
        val method = cl.declaredMethods.first()
        return method.invoke(null, Icons.Filled) as ImageVector
    }
}