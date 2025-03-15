package com.ideasapp.petemotions.presentation.ui.reusableElements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


//TODO amend color, onclick, text
@Composable
fun MonthDropDownMenu() {
    var expanded by remember {mutableStateOf(false)}
    Box(modifier=Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("curr month")
            IconButton(onClick={ expanded =! expanded }) {
                Icon(Icons.Default.ArrowDropDown,contentDescription="More options")
            }
        }
        DropdownMenu(expanded=expanded,onDismissRequest={expanded=false}) {
            DropdownMenuItem(text={Text("Option 1")},onClick={ /* TODO */ })
            DropdownMenuItem(text={Text("Option 2")},onClick={ /* TODO */})
        }
    }
}