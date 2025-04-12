package com.ideasapp.petemotions.presentation.ui.screens.timetable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import kotlinx.coroutines.flow.Flow


@Composable
fun FullTimetableScreen(
    timetableFlow: Flow<PagingData<TimetableItem>>,
    onAddTimetableItem : (TimetableItem) -> Unit,
    onDeleteTimetableItem : (TimetableItem) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var showPermissionDialog = remember { mutableStateOf(true) }
    var selectedItem by remember { mutableStateOf<TimetableItem?>(null) }

    val lazyTimetableItems = timetableFlow.collectAsLazyPagingItems()

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
                onClick = { openDialog(null) },
                modifier = Modifier.padding(16.dp).background(MainTheme.colors.singleTheme),
                containerColor = MainTheme.colors.mainColor
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).background(MainTheme.colors.singleTheme)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = lazyTimetableItems.itemCount,
                    key = lazyTimetableItems.itemKey {it.id},
                    contentType = lazyTimetableItems.itemContentType { "TimetableItem" }
                ) { index ->
                    val item = lazyTimetableItems[index]
                    if (item != null) {
                        ListItem(
                            item = item,
                            onClick = {
                                openDialog(item)
                            },
                            onLongClick = {
                                onDeleteTimetableItem(item)
                            },
                        )
                    }
                }
                // paging settings
                lazyTimetableItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                        loadState.append is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = loadState.refresh as LoadState.Error
                            item {
                                Text(
                                    text = "Error: ${e.error.localizedMessage}",
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
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
                Log.d("Timetable", "New item added: ${newItem.description}")
                onAddTimetableItem(newItem)
                closeDialog()
            }
        )
    }
}
