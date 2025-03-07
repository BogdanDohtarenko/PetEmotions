package com.ideasapp.petemotions.presentation.ui.screens.timetable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.paging.compose.LazyPagingItems
import com.ideasapp.petemotions.domain.entity.calendar.CalendarUiState
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel


//TODO  add db
@Composable
fun FullTimetableScreen(
    timetableFlow: LazyPagingItems<TimetableItem>,
    onAddTimetableItem : (TimetableItem) -> Unit,
) {

    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<TimetableItem?>(null) }

    fun openDialog(item: TimetableItem?) {
        selectedItem = item
        showDialog = true
    }

    fun closeDialog() {
        showDialog = false
        selectedItem = null
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openDialog(null)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = timetableFlow.itemCount,
                    key = { index ->
                        val item = timetableFlow[index]
                        item?.id ?: index
                    },
                    contentType = { _ ->
                        TimetableItem::class
                    }
                ) { index ->
                    val item = timetableFlow[index]
                    if (item != null) {
                        ListItem(
                            item = item,
                            onClick = { openDialog(item) }
                        )
                    }
                }
            }
        }
    }


    if (showDialog) {
        EditTimetableDialog(
            item = selectedItem,
            onDismiss = { closeDialog() },
            onSave = { newItem ->
                if (selectedItem == null) {
                    Log.d("Timetable", "New item added $newItem ") //TODO
                } else {
                    Log.d("Timetable", "New item edited $newItem")
                }
                closeDialog()
            }
        )
    }
}

