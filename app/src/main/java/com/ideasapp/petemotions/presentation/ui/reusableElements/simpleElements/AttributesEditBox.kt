package com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.presentation.ui.screens.calendar.AddEditAttributeDialog
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun AttributesEditBox(
    titleText: String,
    modifier : Modifier = Modifier,
    textColor: Color,
    attributeBoxType: String,
    addAttributeState: MutableState<Boolean>,
    possibleIconsList: List<Int>,
    dayAttributesList: List<DayAttribute>,
    onAddAttributeClick: (DayAttribute) -> Unit,
    onDeleteAttributeClick: (DayAttribute) -> Unit,
) {
    val haptic = LocalHapticFeedback.current
    var addingAttribute by remember { mutableStateOf(false) }
    val selectedItemState = remember { mutableStateOf<DayAttribute?>(null) }
        Box(
            modifier = modifier
                .wrapContentHeight()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(MainTheme.colors.warningModeColor) // Replace with MainTheme.colors.spareContentColor
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Image(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        modifier = modifier.clickable { addAttributeState.value = false }
                    )
                    Text(
                        text = titleText,
                        modifier = modifier.padding(horizontal = 10.dp),
                        fontSize = 16.sp
                    )
                }
                // Grid implementation
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4), // 4 items per row
                    modifier = modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(dayAttributesList) { attribute ->
                        AttributeItem(
                            imageVector = attribute.imageVectorResource,
                            textColor = textColor,
                            title = attribute.title,
                            isChosen = false,
                            modifier = Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            selectedItemState.value = attribute
                                            addingAttribute = true
                                        },
                                        onLongPress = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            selectedItemState.value = attribute
                                            onDeleteAttributeClick(attribute)
                                        },
                                    )
                                }
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "add attribute",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                selectedItemState.value = null
                                addingAttribute = true
                            }
                    )
                }
            }
        }
    if (addingAttribute) {
        AddEditAttributeDialog(
            itemState = selectedItemState,
            attributeBoxType = attributeBoxType,
            onDismiss = { addingAttribute = false },
            onSave = onAddAttributeClick,
            possibleIconsList = possibleIconsList
        )
    }
}
