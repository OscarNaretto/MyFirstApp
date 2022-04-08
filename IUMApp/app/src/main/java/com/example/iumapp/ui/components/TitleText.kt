package com.example.iumapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TitleText(textVal: String, fontWeight: FontWeight) {
    Text(
        text = textVal,
        style = MaterialTheme.typography.h6.copy(
            fontWeight = fontWeight
        ),
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)
    )
}