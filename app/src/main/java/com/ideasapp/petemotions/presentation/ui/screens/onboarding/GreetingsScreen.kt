package com.ideasapp.petemotions.presentation.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.CustomTextField
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme
import com.ideasapp.petemotions.presentation.viewModels.CalendarViewModel
import kotlinx.coroutines.launch

@Composable
fun GreetingsScreen(
    calendarViewModel: CalendarViewModel,
    onExitClick: () -> Unit
) {
    var showAddingDialog by remember { mutableStateOf(false) }
    val petName = remember { mutableStateOf("") }
    var petIndex by remember { mutableIntStateOf(0) }
    val petList = remember { mutableStateListOf<Pet>() }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        backgroundColor = MainTheme.colors.singleTheme,
        contentColor = MainTheme.colors.mainColor,
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Welcome to my app",
                    color = MainTheme.colors.mainColor,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(20.dp)
                )
                Text(
                    text = "It should help you with tracking the status of all your pets",
                    color = MainTheme.colors.mainColor,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(paddingValues)
                )
                Text(
                    text = "First of all let's add your pets:",
                    color = MainTheme.colors.mainColor,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(20.dp)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(MainTheme.colors.spareContentColor)
                        .padding(10.dp)
                ) {
                    Column {
                        CustomTextField(description = petName, label = "Pet name")
                        TextButton(
                            onClick = {
                                showAddingDialog = true
                            },
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text(
                                "Add pet",
                                color = MainTheme.colors.singleTheme,
                                fontSize = 18.sp,
                            )
                        }
                    }
                }
                Text(
                    text = "Your pets (in the next screen you always can delete):",
                    color = MainTheme.colors.mainColor,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(20.dp)
                )
                if (petList.size > 0) {
                LazyColumn(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(MainTheme.colors.mainColor)
                        .padding(10.dp)
                        .heightIn(max = 200.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(petList) { item ->
                        Text(
                            text = item.name,
                            fontSize = 16.sp,
                            color = MainTheme.colors.singleTheme,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
                } else {
                    Text(
                        text = "No pets yet",
                        fontSize = 16.sp,
                        color = MainTheme.colors.mainColor,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                if (petList.size > 0) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                petList.forEach {item->
                                    calendarViewModel.addPet(item)
                                }
                                onExitClick()
                            }
                    },
                        colors = ButtonColors(containerColor = MainTheme.colors.singleTheme,disabledContainerColor = MainTheme.colors.singleTheme,contentColor = MainTheme.colors.mainColor,disabledContentColor = MainTheme.colors.spareContentColor),
                        modifier = Modifier.padding(40.dp).align(Alignment.End)
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center) {
                            Text(text = "Next", fontSize = 18.sp, color = MainTheme.colors.mainColor)
                            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,tint = MainTheme.colors.mainColor,contentDescription = "next step")
                        }
                    }
                }
            }
        }
    if (showAddingDialog) {
        AlertDialog(
            icon = {
                Icon(Icons.Default.AddCircle, tint = Color.Black, contentDescription = "Warning")
            },
            text = {
                Text(
                    text = "You are going to add pet: ${petName.value}.",
                    color = MainTheme.colors.singleTheme
                )
            },
            onDismissRequest = {
                showAddingDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newPet = Pet(petIndex++, petName.value)
                        petList.add(newPet)
                        showAddingDialog = false
                    }
                ) {
                    Text("Confirm", color = MainTheme.colors.singleTheme)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddingDialog = false
                    }
                ) {
                    Text("Dismiss", color = MainTheme.colors.singleTheme)
                }
            },
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(MainTheme.colors.singleTheme),
            containerColor = MainTheme.colors.mainColor,
            textContentColor = MainTheme.colors.singleTheme,
            tonalElevation = 8.dp
        )
    }
}
}