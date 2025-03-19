package com.ideasapp.petemotions.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ideasapp.petemotions.data.db.dbModels.DayItemInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CalendarListDao {

    @Query("SELECT * FROM DayInfo WHERE petId = :petId")
    fun getDayInfoList(petId: Int): Flow<List<DayItemInfoDbModel>>

    @Query("SELECT * FROM DayInfo WHERE date = :id LIMIT 1")
    suspend fun getItemDayInfo(id: Long): DayItemInfoDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemDayInfo(ideaItemDbModel : DayItemInfoDbModel)

}
