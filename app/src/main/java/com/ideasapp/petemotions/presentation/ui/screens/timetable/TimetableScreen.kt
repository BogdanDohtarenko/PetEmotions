package com.ideasapp.petemotions.presentation.ui.screens.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems

import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem
import com.ideasapp.petemotions.presentation.viewModels.TimetableViewModel

@Composable
fun FullTimetableScreen(viewModel: TimetableViewModel)  {
    val timetableFlow = viewModel.getTimetableFlow().collectAsLazyPagingItems()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //TODO
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
                        TimetableItem::class //set type to prevent freeze
                    }
                ) { index ->
                    val item = timetableFlow[index]
                    if (item != null) {
                        ListItem(item = item)
                    }
                }
            }
        }
    }
}


@Composable
fun ListItem(item: TimetableItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
            .padding(8.dp)
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