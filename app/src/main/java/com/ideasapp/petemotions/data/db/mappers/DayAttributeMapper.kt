package com.ideasapp.petemotions.data.db.mappers

import com.ideasapp.petemotions.data.db.dbModels.DayAttributesDbModel
import com.ideasapp.petemotions.data.db.dbModels.DayItemInfoDbModel
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.domain.entity.calendar.DayItemInfo

object DayAttributeMapper {
    fun dbModelToEntity(dbModel: DayAttributesDbModel):DayAttribute {
        return DayAttribute(id = dbModel.id, type = dbModel.type, title = dbModel.title, imageVectorResource = dbModel.imageVectorResource)
    }
    fun entityToDbModel(entity: DayAttribute):DayAttributesDbModel {
        return DayAttributesDbModel(id = entity.id, type = entity.type, title = entity.title, imageVectorResource = entity.imageVectorResource)
    }
}