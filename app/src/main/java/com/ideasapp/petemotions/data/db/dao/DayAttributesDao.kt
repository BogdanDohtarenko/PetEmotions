package com.ideasapp.petemotions.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ideasapp.petemotions.data.db.dbModels.DayAttributesDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DayAttributesDao {
    @Query("SELECT * FROM DayAttributes")
    fun getDayAttributeFlowList(): Flow<List<DayAttributesDbModel>>

    @Query("SELECT * FROM DayAttributes")
    fun getDayAttributeList(): List<DayAttributesDbModel>

    @Query("DELETE FROM DayAttributes WHERE id = :dayAttributeId")
    fun deleteDayAttribute(dayAttributeId :Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDayAttribute(dayAttribute :DayAttributesDbModel)
}