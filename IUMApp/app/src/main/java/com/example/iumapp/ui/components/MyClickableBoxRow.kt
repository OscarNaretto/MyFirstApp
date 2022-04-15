package com.example.iumapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyClickableBoxRow(
    chips: List<String>,
    current: MutableState<String>
){
    var selectedChipIndex by remember { mutableStateOf(0) }

    LazyRow {
        items(chips.size) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 10.dp, top = 15.dp, bottom = 15.dp, end = 10.dp)
                    .clickable {
                        selectedChipIndex = it
                        current.value = chips[it]
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selectedChipIndex == it) Color(0xFF6200EE)
                        else Color.Gray
                    )
                    .padding(15.dp),
            ) {
                Text(text = chips[it], color = Color.White)
            }
        }
    }
}