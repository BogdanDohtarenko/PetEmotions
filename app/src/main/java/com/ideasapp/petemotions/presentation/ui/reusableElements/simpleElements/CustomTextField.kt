package com.ideasapp.petemotions.presentation.ui.reusableElements.simpleElements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.ideasapp.petemotions.presentation.ui.theme.MainTheme

@Composable
fun CustomTextField(description: MutableState<String>) {
    TextField(
        value = description.value,
        onValueChange = { description.value = it },
        label = { Text("Description") },
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
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CustomTextField(description: MutableState<String>, label: String) {
    TextField(
        value = description.value,
        onValueChange = { description.value = it },
        label = { Text(label) },
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
        modifier = Modifier.fillMaxWidth()
    )
}

