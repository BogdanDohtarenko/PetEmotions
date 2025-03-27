package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ideasapp.petemotions.presentation.navigation.BottomNavItem
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedItemIndex by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.07f),
        color = MainTheme.colors.navigationBarColor,
        shape = AnimatedBottomBarShape(selectedItemIndex)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem.entries.forEach { item ->
                val isSelected = item.id == selectedItemIndex
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedItemIndex = item.id
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                        .padding(
                            bottom = if (isSelected) 18.dp else 0.dp,
                            start = 0.dp,
                            end = 0.dp,
                            top = 0.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) MainTheme.colors.mainColor else MainTheme.colors.singleTheme,
                        modifier = Modifier
                            .size(if (isSelected) 36.dp else 28.dp)
                    )
                }
            }
        }
    }
}

class AnimatedBottomBarShape(private val selectedItemIndex: Int) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val hillHeight = with(density) { 25.dp.toPx() }
        val hillWidth = with(density) { 130.dp.toPx() }

        return Outline.Generic(Path().apply {
            // Начинаем с левого верхнего угла
            moveTo(0f, 0f)

            // Линия до начала горки (слева)
            lineTo(calculateHillStartX(size.width, selectedItemIndex, hillWidth), 0f)

            // Рисуем горку (кривая Безье)
            cubicTo(
                x1 = calculateHillStartX(size.width, selectedItemIndex, hillWidth) + hillWidth/4,
                y1 = -hillHeight,  // Отрицательное значение, чтобы горка "выступала" вверх
                x2 = calculateHillStartX(size.width, selectedItemIndex, hillWidth) + hillWidth*3/4,
                y2 = -hillHeight,
                x3 = calculateHillStartX(size.width, selectedItemIndex, hillWidth) + hillWidth,
                y3 = 0f
            )

            // Линия до правого края
            lineTo(size.width, 0f)

            // Нижняя часть (прямоугольник)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        })
    }

    private fun calculateHillStartX(
        totalWidth: Float,
        selectedIndex: Int,
        hillWidth: Float
    ): Float {
        val itemCount = 3  // Количество элементов в BottomNavigation
        val itemWidth = totalWidth / itemCount
        val itemCenter = itemWidth * (selectedIndex + 0.5f)
        return itemCenter - hillWidth/2
    }
}