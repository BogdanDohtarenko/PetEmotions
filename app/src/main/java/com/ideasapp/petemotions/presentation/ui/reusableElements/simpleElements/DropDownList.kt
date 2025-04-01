package com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun DropdownList(
    itemList: List<String>,
    selectedIndex: MutableState<Int>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier.wrapContentSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier
                .clickable { showDropdown = !showDropdown }
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = itemList[selectedIndex.value],
                    color = colors.onSurface,
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Icon(
                    imageVector = if (showDropdown)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown arrow",
                )
            }
        }

        if (showDropdown) {
            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = { showDropdown = false },
                modifier = Modifier
                    .background(MainTheme.colors.mainColor)
                    .width(IntrinsicSize.Min)
            ) {
                itemList.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                color = if (index == selectedIndex.value)
                                    MainTheme.colors.singleTheme.copy(alpha = 0.5f)
                                else
                                    MainTheme.colors.singleTheme,
                                fontSize = 14.sp
                            )
                        },
                        onClick = {
                            onItemClick(index)
                            selectedIndex.value = index
                            showDropdown = false
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = MainTheme.colors.singleTheme
                        )
                    )
                    if (index != itemList.lastIndex) {
                        HorizontalDivider(
                            color = MainTheme.colors.singleTheme.copy(alpha = 0.2f),
                            thickness = 0.5.dp
                        )
                    }
                }
            }
        }
    }
}