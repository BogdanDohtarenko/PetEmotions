package com.ideasapp.petemotions.data.db

import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem

object TimetableMapper {
    fun dbModelToEntity(dbModel: TimetableItemDbModel): TimetableItem {
        return TimetableItem(id = dbModel.id, description = dbModel.description, dateTime = dbModel.dateTime)
    }
    fun entityToDbModel(entity: TimetableItem): TimetableItemDbModel {
        return TimetableItemDbModel(id = entity.id, description = entity.description, dateTime = entity.dateTime)
    }
}