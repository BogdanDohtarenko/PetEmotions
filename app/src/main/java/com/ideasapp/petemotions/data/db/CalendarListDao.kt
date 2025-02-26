package com.ideasapp.petemotions.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface CalendarListDao {

    @Query("SELECT * FROM DayInfo")
    fun getDayInfoList(): Flow<List<DayItemInfoDbModel>>

    @Query("SELECT * FROM DayInfo WHERE date = :id LIMIT 1")
    suspend fun getItemDayInfo(id: Int): DayItemInfoDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemDayInfo(ideaItemDbModel : DayItemInfoDbModel)

}
