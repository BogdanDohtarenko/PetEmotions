package com.ideasapp.petemotions.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ideasapp.petemotions.data.db.TimetableItemDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TimetableDao {

    @Query("SELECT * FROM Timetable ORDER BY id LIMIT :limit OFFSET :offset")
    suspend fun getTimetableListPaged(limit: Int, offset: Int): List<TimetableItemDbModel>

    @Query("SELECT * FROM Timetable WHERE id = :id LIMIT 1")
    suspend fun getTimetableItem(id: Int): TimetableItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTimetableItem(ideaItemDbModel: TimetableItemDbModel)

    @Query("DELETE FROM Timetable WHERE id = :id")
    suspend fun deleteTimetableItem(id: Int): Int

}
