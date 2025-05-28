package com.ideasapp.petemotions.domain.entity.calendar


data class DayAttribute(
    val imageVectorResource: Int,
    val title : String,
    val id: Int = 0,
    val type: String,
) {
    companion object {
        const val ATTRIBUTE_TYPE_HEALTH = "Health"
        const val ATTRIBUTE_TYPE_FOOD = "Food"
        const val ATTRIBUTE_TYPE_EVENTS = "Events"
    }
}