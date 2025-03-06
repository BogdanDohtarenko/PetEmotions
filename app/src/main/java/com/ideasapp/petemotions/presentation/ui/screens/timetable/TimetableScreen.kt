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
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel


//TODO divide into diff files, amend all if necessary, add db
//TODO screen flickers, move view model up ^
@Composable
fun FullTimetableScreen(viewModel: TimetableViewModel) {
    val timetableFlow = viewModel.getTimetableFlow().collectAsLazyPagingItems()
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

@Composable
fun ListItem(item: TimetableItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = item.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = item.dateTime,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun EditTimetableDialog(
    item: TimetableItem?,
    onDismiss: () -> Unit,
    onSave: (TimetableItem) -> Unit
) {
    var description by remember { mutableStateOf(item?.description ?: "") }
    var dateTime by remember { mutableStateOf(item?.dateTime ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (item == null) "Add" else "Edit") },
        text = {
            Column {
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = dateTime,
                    onValueChange = { dateTime = it },
                    label = { Text("Date and time") }, //TODO change format
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newItem = TimetableItem(
                        id = item?.id ?: -1,
                        description = description,
                        dateTime = dateTime
                    )
                    onSave(newItem)
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}