package com.ideasapp.petemotions.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimetableDao {

    @Query("SELECT * FROM Timetable")
    fun getTimetableList(): Flow<List<DayItemInfoDbModel>>

    @Query("SELECT * FROM Timetable WHERE date = :id LIMIT 1")
    suspend fun getTimetableItem(id: Int): DayItemInfoDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTimetableItem(ideaItemDbModel : DayItemInfoDbModel)

}
