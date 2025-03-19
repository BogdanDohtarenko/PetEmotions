package com.ideasapp.petemotions.presentation.navigation


sealed class NavItem(val route: String, val label: String) {
    object EditDay: NavItem(ROUTE_EDIT_DAY, "EditDay")

    companion object {
        const val ROUTE_EDIT_DAY = "EditDayInfo"
        const val DATE_PARAM = "date"
        const val PET_PARAM = "petId"
    }
}