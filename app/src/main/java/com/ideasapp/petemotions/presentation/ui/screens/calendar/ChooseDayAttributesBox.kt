package com.ideasapp.petemotions.presentation.ui.screens.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.domain.entity.calendar.DayAttribute
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.AttributeItem

@Composable
fun ChooseDayAttributesBox(
    textColor: Color,
    addAttributeState: MutableState<Boolean>,
    dayAttributesForFirstRow: List<DayAttribute>,
    dayAttributesOptional : List<DayAttribute>?,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.Center), verticalArrangement = Arrangement.Center) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    dayAttributesForFirstRow.take(4).forEach { attribute ->
                        AttributeItem(
                            imageVector = attribute.imageVector,
                            textColor = textColor,
                            title = attribute.title
                        )
                    }
                }
                //optional rows
                dayAttributesOptional
                    ?.chunked(4)
                    ?.forEach { rowAttributes ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (i in 0 until 4) {
                                val attribute = rowAttributes.getOrNull(i)
                                if (attribute != null) {
                                    AttributeItem(
                                        imageVector = attribute.imageVector,
                                        textColor = textColor,
                                        title = attribute.title
                                    )
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //TODO open distinct screens depends on attribute type
                    //TODO amend add button
                    AttributeItem(
                        imageVector = Icons.Default.Settings,
                        textColor = textColor,
                        title = "_____",
                        modifier = Modifier.clickable {
                            addAttributeState.value = true
                        }
                    )
                }
            }
        }
    }
}