package com.ideasapp.petemotions.data.db.mappers

import com.ideasapp.petemotions.data.db.DayItemInfoDbModel
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo

object DayInfoMapper {
    fun dbModelToEntity(dbModel: DayItemInfoDbModel): DayItemInfo {
        return DayItemInfo(date = dbModel.date, mood = dbModel.mood)
    }
    fun entityToDbModel(entity:DayItemInfo): DayItemInfoDbModel {
        return DayItemInfoDbModel(date = entity.date, mood = entity.mood)
    }
}