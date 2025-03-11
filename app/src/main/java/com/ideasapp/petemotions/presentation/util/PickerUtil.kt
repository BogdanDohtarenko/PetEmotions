package com.ideasapp.petemotions.presentation.util

object PickerUtil {
    fun Int.getTimeDefaultStr(): String =  "${if (this <= 9) "0" else ""}$this"

    internal const val COUNT_OF_VISIBLE_ITEMS = 5

    internal const val ITEM_HEIGHT = 35f

    internal const val LIST_HEIGHT = COUNT_OF_VISIBLE_ITEMS * ITEM_HEIGHT
}

