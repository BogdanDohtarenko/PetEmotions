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
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
    var newPetName by remember { mutableStateOf(TextFieldValue("")) }

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
                LazyRow(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp)
                ) {
                    items(
                        items = pets,
                        key = { it.id }
                    ) { pet ->
                        Text(
                            text = pet.name,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .background(
                                    color = MainTheme.colors.mainColor,
                                    shape = RoundedCornerShape(8.dp)
                                )
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
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainTheme.colors.mainColor,
                        contentColor = MainTheme.colors.singleTheme
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Add Pet")
                }
            }
        }

        if (showWarningDialog) {
            AlertDialog(
                icon = {
                    Icon(Icons.Default.Info, tint = Color.Black, contentDescription = "Warning")
                },
                title = {
                    Text(text = "Warning", color = MainTheme.colors.singleTheme)
                },
                text = {
                    Text(
                        text = "You are going to delete this pet and all associated data.",
                        color = MainTheme.colors.singleTheme
                    )
                },
                onDismissRequest = {
                    showWarningDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val pet = petToDelete.value
                            if (pet != null) {
                                deletePet(pet)
                            }
                            showWarningDialog = false
                        }
                    ) {
                        Text("Confirm", color = MainTheme.colors.singleTheme)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showWarningDialog = false
                        }
                    ) {
                        Text("Dismiss", color = MainTheme.colors.singleTheme)
                    }
                },
                modifier = Modifier.background(MainTheme.colors.singleTheme),
                containerColor = MainTheme.colors.mainColor,
                textContentColor = MainTheme.colors.singleTheme,
                tonalElevation = 8.dp
            )
        }

        if (showAddingDialog) {
            AlertDialog(
                icon = {
                    Icon(Icons.Default.Add, tint = Color.Black, contentDescription = "Add Pet")
                },
                title = {
                    Text(text = "Add New Pet", color = MainTheme.colors.singleTheme)
                },
                text = {
                    Column {
                        Text(
                            text = "Enter the name of your new pet:",
                            color = MainTheme.colors.singleTheme
                        )
                        OutlinedTextField(
                            value = newPetName,
                            onValueChange = { newPetName = it },
                            label = { Text("Pet Name", color = MainTheme.colors.singleTheme) },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MainTheme.colors.mainColor,
                                unfocusedTextColor = MainTheme.colors.singleTheme,
                                focusedContainerColor = MainTheme.colors.mainColor,
                                focusedTextColor = MainTheme.colors.singleTheme,
                                cursorColor = MainTheme.colors.singleTheme,
                                focusedIndicatorColor = MainTheme.colors.singleTheme,
                                unfocusedIndicatorColor = MainTheme.colors.singleTheme.copy(alpha = 0.5f),
                                focusedLabelColor = MainTheme.colors.singleTheme,
                                unfocusedLabelColor = MainTheme.colors.singleTheme.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .background(MainTheme.colors.mainColor)
                        )
                    }
                },
                onDismissRequest = {
                    showAddingDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (newPetName.text.isNotBlank()) {
                                val newPet = Pet(id = pets.size + 1, name = newPetName.text)
                                addPet(newPet)
                                newPetName = TextFieldValue("")
                                showAddingDialog = false
                            }
                        }
                    ) {
                        Text("Add", color = MainTheme.colors.singleTheme)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showAddingDialog = false
                        }
                    ) {
                        Text("Cancel", color = MainTheme.colors.singleTheme)
                    }
                },
                modifier = Modifier.background(MainTheme.colors.singleTheme),
                containerColor = MainTheme.colors.mainColor,
                textContentColor = MainTheme.colors.singleTheme,
                tonalElevation = 8.dp
            )
        }
    }
}