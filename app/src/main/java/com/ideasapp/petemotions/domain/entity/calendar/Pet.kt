package com.ideasapp.petemotions.domain.entity.calendar

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList

data class Pet(
    val id: Int,
    val name: String,
    //TODO val avatar: ImageVector
) {
    companion object {
        @ExperimentalCoroutinesApi
        suspend fun Flow<List<Pet>>.flattenToList() =
             flatMapConcat { it.asFlow() }.toList()
    }
}