package com.ideasapp.petemotions.data.db

import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo

object DayInfoMapper {
    fun dbModelToEntity(dbModel:DayItemInfoDbModel): DayItemInfo {
        return DayItemInfo(date = dbModel.date, mood = dbModel.mood)
    }
}