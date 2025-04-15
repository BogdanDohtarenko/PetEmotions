package com.ideasapp.petemotions.presentation.ui.reusableElements.pickers

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements.AttributeItem

@Composable
fun IconsPicker(
    iconsList: List<Int>,
    onIconClick: (ImageVector) -> Unit,
    modifier : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp,Color.Black)
            .padding(4.dp)
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(4), // 4 items per row
            modifier = modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
                .padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(iconsList) {icon->
                val imageVector = ImageVector.vectorResource(id = icon)
                Image(imageVector = imageVector, contentDescription = icon.toString(), modifier = modifier.clickable {
                    onIconClick(imageVector)
                    }
                )
            }
        }
    }
}