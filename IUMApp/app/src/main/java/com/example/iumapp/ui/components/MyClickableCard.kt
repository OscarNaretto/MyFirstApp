package com.example.iumapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyClickableCard(
    backgroundColor: Color = MaterialTheme.colors.primary,
    CardContent: @Composable () -> Unit,
    onClickAction: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        backgroundColor = backgroundColor,
        elevation = 20.dp,
        content = CardContent,
        onClick = onClickAction
    )
}