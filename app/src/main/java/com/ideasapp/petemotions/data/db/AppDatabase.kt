package com.ideasapp.petemotions.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DayItemInfoDbModel::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun CalendarListDao(): CalendarListDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        const val DATABASE_NAME = "pet_emotions.db"

        fun getInstance(application :Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
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