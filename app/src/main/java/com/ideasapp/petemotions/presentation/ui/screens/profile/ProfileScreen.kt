package com.ideasapp.petemotions.presentation.ui.screens.profile

import androidx.compose.material.BottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    showBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    coroutineScope: CoroutineScope
) {
    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = bottomSheetState
    ) {
        // Sheet content
        Button(onClick = {
            coroutineScope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    showBottomSheet.value = false
                }
            }
        }) {
            Text("Hide bottom sheet")
        }
    }
}
