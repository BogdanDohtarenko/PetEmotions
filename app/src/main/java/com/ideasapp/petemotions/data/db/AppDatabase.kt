package com.ideasapp.petemotions.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ideasapp.petemotions.data.db.converters.ImageVectorConverter
import com.ideasapp.petemotions.data.db.converters.StringListConverter
import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.data.db.dao.DayAttributesDao
import com.ideasapp.petemotions.data.db.dao.TimetableDao
import com.ideasapp.petemotions.data.db.dbModels.DayAttributesDbModel
import com.ideasapp.petemotions.data.db.dbModels.DayItemInfoDbModel
import com.ideasapp.petemotions.data.db.dbModels.TimetableItemDbModel

@Database(entities = [
    DayItemInfoDbModel::class,
    TimetableItemDbModel::class,
    DayAttributesDbModel::class],
    version = 1,
    exportSchema = false)
@TypeConverters(ImageVectorConverter::class, StringListConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun CalendarListDao(): CalendarListDao
    abstract fun TimetableDao(): TimetableDao
    abstract fun DayAttributesDao():DayAttributesDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DATABASE_NAME = "pet_emotions.db"

        fun getInstance(application: Context): AppDatabase {
            INSTANCE?.let { //to prevent synchronized block
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let { //if other thread create instance when we wait
                    return it
                }
                val db =  Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                INSTANCE = db
                return db
            }
        }
    }

}