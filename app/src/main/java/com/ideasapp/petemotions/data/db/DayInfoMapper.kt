package com.ideasapp.petemotions.data.db

import com.ideasapp.petemotions.domain.entity.calendar.DayInfoItem

object DayInfoMapper {
    fun dbModelToEntity(dbModel:DayItemInfoDbModel): DayInfoItem {
        return DayInfoItem(dbModel.date, dbModel.mood)
    }
}