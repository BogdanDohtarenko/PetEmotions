package com.ideasapp.petemotions.presentation.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    showBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    pets: List<Pet>,
    deletePet: (Pet) -> Unit,
    addPet: (Pet) -> Unit
) {
    var showWarningDialog by remember { mutableStateOf(false) }
    var showAddingDialog by remember { mutableStateOf(false) }
    val petToDelete = remember { mutableStateOf<Pet?>(null) }
    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = bottomSheetState,
        containerColor = MainTheme.colors.singleTheme,
        contentColor = MainTheme.colors.mainColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Your pets",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp)
                )
                LazyRow (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp)
                )  {
                    items(
                        items = pets,
                        key = { it.id }
                    ) { pet ->
                        Text(
                            text = pet.name,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 8.dp,vertical = 4.dp)
                                .background(color = MainTheme.colors.mainColor,shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            showWarningDialog = true
                                            petToDelete.value = pet
                                        },
                                    )
                                },
                            color = MainTheme.colors.singleTheme
                        )
                    }
                }
                Button(
                    onClick = {
                        showAddingDialog = true
                    }
                ) {

                }
            }
        }
        if (showWarningDialog) {
            AlertDialog(
                icon = {
                    Icon(Icons.Default.Info, contentDescription = "Warning")
                },
                title = {
                    Text(text = "Warning")
                },
                text = {
                    Text(text = "you are going to delete pet and all data")
                },
                onDismissRequest = {
                    showWarningDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val id = petToDelete.value
                            if (id != null)
                                deletePet(id)
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showWarningDialog = false
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
        if (showAddingDialog) {
            AlertDialog(
                icon = {
                    Icon(Icons.Default.Info, contentDescription = "Warning")
                },
                title = {
                    Text(text = "Warning")
                },
                text = {
                    Text(text = "you are going to delete pet and all data")
                },
                onDismissRequest = {
                    showWarningDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val id = petToDelete.value
                            if (id != null)
                                deletePet(id)
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showWarningDialog = false
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}
