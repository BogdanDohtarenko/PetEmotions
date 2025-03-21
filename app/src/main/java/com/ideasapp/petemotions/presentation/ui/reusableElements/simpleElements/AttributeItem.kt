package com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun AttributeItem(
    imageVector: ImageVector,
    textColor: Color,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = title,
            tint = textColor,
            modifier = Modifier
                .size(14.dp)
                .padding(bottom = 4.dp)
        )
        Text(
            text = title,
            color = textColor,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}