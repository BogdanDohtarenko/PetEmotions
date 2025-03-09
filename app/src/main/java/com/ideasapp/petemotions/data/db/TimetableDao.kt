package com.ideasapp.petemotions.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimetableDao {

    @Query("SELECT * FROM Timetable")
    fun getTimetableList(): List<TimetableItemDbModel>

    @Query("SELECT * FROM Timetable WHERE id = :id LIMIT 1")
    suspend fun getTimetableItem(id: Int): TimetableItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTimetableItem(ideaItemDbModel: TimetableItemDbModel)

    @Query("DELETE FROM Timetable WHERE id = :id")
    suspend fun deleteTimetableItem(id: Int): Int

}
