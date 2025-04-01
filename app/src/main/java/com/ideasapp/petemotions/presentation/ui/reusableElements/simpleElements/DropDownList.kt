package com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun DropdownList(
    itemList: List<String>,
    selectedIndex: MutableState<Int>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    // State to control the dropdown visibility
    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Button to toggle the dropdown
        Box(
            modifier = Modifier
                .clickable { showDropdown = true }
                .padding(5.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector =
                    if (!showDropdown)
                        Icons.Default.KeyboardArrowDown
                    else
                        Icons.Default.KeyboardArrowUp,
                    contentDescription = "show dropdown"
                )
                Text(
                    text = itemList[selectedIndex.value],
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Dropdown list as an overlay
        if (showDropdown) {
            Popup(
                alignment = Alignment.TopCenter,
                properties = PopupProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                onDismissRequest = { showDropdown = false }
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFFE8F5E9)) // Light green background
                        .border(1.dp, Color.Gray)
                        .wrapContentWidth() // Ensure size matches content
                        .verticalScroll(scrollState)
                        .padding(vertical = 4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    itemList.forEachIndexed { index, item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onItemClick(index)
                                    selectedIndex.value = index
                                    showDropdown = false
                                }
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp
                            )
                        }
                        if (index != itemList.lastIndex) {
                            Divider(color = Color.LightGray, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}