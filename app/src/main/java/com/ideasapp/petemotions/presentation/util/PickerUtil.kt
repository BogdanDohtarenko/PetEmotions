package com.ideasapp.petemotions.presentation.util

import android.content.Context
import androidx.compose.foundation.lazy.LazyListState
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.abs

object PickerUtil {
    fun Int.getTimeDefaultStr(): String =  "${if (this <= 9) "0" else ""}$this"

    internal const val COUNT_OF_VISIBLE_ITEMS = 3

    internal const val ITEM_HEIGHT = 35f

    internal const val LIST_HEIGHT = COUNT_OF_VISIBLE_ITEMS * ITEM_HEIGHT

    fun Int.pixelsToDp(context: Context): Float {
        val densityDpi = context.resources.displayMetrics.densityDpi
        return this / (densityDpi / 160f)
    }

    internal fun LazyListState.itemForScrollTo(context: Context): Int {
        val offset = firstVisibleItemScrollOffset.pixelsToDp(context)
        return when {
            offset == 0f -> firstVisibleItemIndex
            offset % ITEM_HEIGHT >= ITEM_HEIGHT / 2 -> firstVisibleItemIndex + 1
            else -> firstVisibleItemIndex
        }
    }

    fun LocalDate.getDateStringWithWeekOfDay(context: Context): String {
        val dayOfWeek = this.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        return "${this.dayOfMonth} ${this.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())} ($dayOfWeek)"
    }

    internal fun calculateScaleX(listState: LazyListState, index: Int): Float {
        // Получаем информацию о текущем состоянии компоновки списка
        val layoutInfo = listState.layoutInfo
        // Извлекаем индексы видимых элементов
        val visibleItems = layoutInfo.visibleItemsInfo.map { it.index }
        // Если элемент не виден, возвращаем масштаб 1 (нормальный)
        if (!visibleItems.contains(index)) return 1f
        // Находим информацию о конкретном элементе по индексу
        val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return 1f
        // Вычисляем центр видимой области
        val center = (layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset) / 2f
        // Вычисляем расстояние от центра до середины элемента
        val distance = abs((itemInfo.offset + itemInfo.size / 2) - center)
        // Максимальное расстояние до центра для расчета масштаба
        val maxDistance = layoutInfo.viewportEndOffset / 2f
        // Сжимаем элемент до половины при максимальном расстоянии
        return 1f - (distance / maxDistance) * 0.5f
    }

    internal fun calculateScaleY(listState: LazyListState, index: Int): Float {
        // Получаем информацию о текущем состоянии компоновки списка
        val layoutInfo = listState.layoutInfo
        // Извлекаем индексы видимых элементов
        val visibleItems = layoutInfo.visibleItemsInfo.map { it.index }
        // Если элемент не виден, возвращаем масштаб 1 (нормальный)
        if (!visibleItems.contains(index)) return 1f
        // Находим информацию о конкретном элементе по индексу
        val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return 1f
        // Вычисляем центр видимой области
        val center = (layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset) / 2f
        // Вычисляем расстояние от центра до середины элемента
        val distance = abs((itemInfo.offset + itemInfo.size / 2) - center)
        // Максимальное расстояние до центра для расчета масштаба
        val maxDistanceY = layoutInfo.viewportEndOffset / 2f
        // Сжимаем элемент полностью при максимальном расстоянии
        return 1f - (distance / maxDistanceY)
    }

    internal fun calculateAlpha(index: Int, listState: LazyListState): Float {
        // Получаем информацию о текущем состоянии компоновки списка
        val layoutInfo = listState.layoutInfo
        // Извлекаем индексы видимых элементов
        val visibleItems = layoutInfo.visibleItemsInfo.map { it.index }
        // Если нет видимых элементов, возвращаем максимальную непрозрачность
        if (visibleItems.isEmpty()) return 1f
        // Вычисляем центр видимой области
        val center = (layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset) / 2f
        // Находим информацию о конкретном элементе по индексу
        val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return 1f
        // Вычисляем расстояние от центра до середины элемента
        val distance = abs((itemInfo.offset + itemInfo.size / 2) - center)
        // Максимальное расстояние для расчета прозрачности
        val maxDistance = layoutInfo.viewportEndOffset / 2f
        // Уменьшаем прозрачность до 0.3 при максимальном расстоянии
        return 1f - (distance / maxDistance) * 0.7f
    }
}

