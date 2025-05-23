package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.AttributeItem

@Composable
fun ChooseDayAttributesBox(
    textColor: Color,
    editAttributeState: MutableState<Boolean>,
    dayAttributesList: List<DayAttribute>,
    chosenAttributesList: List<String>,
    onChooseAttributeClick: (String) -> Unit,
    modifier : Modifier = Modifier
) {
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
                isChosen = chosenAttributesList.contains(attribute.title),
                modifier = Modifier.clickable {
                    onChooseAttributeClick(attribute.title)
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
            imageVector = Icons.Default.Settings,
            contentDescription = "settings",
            modifier = Modifier
                .size(40.dp)
                .clickable {editAttributeState.value = true}
        )
    }
}