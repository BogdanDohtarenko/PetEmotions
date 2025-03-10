package com.ideasapp.petemotions.data.db.mappers

import com.ideasapp.petemotions.data.db.TimetableItemDbModel
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem

object TimetableMapper {
    fun dbModelToEntity(dbModel: TimetableItemDbModel): TimetableItem {
        return TimetableItem(id = dbModel.id, description = dbModel.description, dateTime = dbModel.dateTime)
    }
    fun entityToDbModel(item: TimetableItem): TimetableItemDbModel {
        return if (item.id == TimetableItem.UNDEFINED_ID) {
            TimetableItemDbModel(
                id = 0,
                description = item.description,
                dateTime = item.dateTime
            )
        } else {
            TimetableItemDbModel(
                id = item.id,
                description = item.description,
                dateTime = item.dateTime
            )
        }
    }
}