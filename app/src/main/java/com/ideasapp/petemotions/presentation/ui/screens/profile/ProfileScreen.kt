package com.ideasapp.petemotions.presentation.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.domain.entity.calendar.Pet
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    showBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    pets: List<Pet>
) {
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
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(10.dp)
                )
                LazyRow (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(20.dp)
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
                                .clickable {

                                },
                            color = MainTheme.colors.singleTheme
                        )
                    }
                }
            }
        }
    }
}
