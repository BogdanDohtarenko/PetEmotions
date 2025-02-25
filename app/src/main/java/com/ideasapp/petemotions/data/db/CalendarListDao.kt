package com.ideasapp.petemotions.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalDate

//TODO end here
@Dao
interface CalendarListDao {

    @Query("SELECT * FROM DayInfo")
    suspend fun getDayInfoList(): List<DayItemInfoDbModel>

    @Query("SELECT * FROM DayInfo WHERE date = :id LIMIT 1")
    suspend fun getItemDayInfo(id: Int): DayItemInfoDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemDayInfo(ideaItemDbModel : DayItemInfoDbModel)

}
