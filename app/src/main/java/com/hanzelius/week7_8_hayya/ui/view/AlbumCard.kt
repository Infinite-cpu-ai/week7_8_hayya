package com.hanzelius.week7_8_hayya.ui.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanzelius.week7_8_hayya.R
import com.hanzelius.week7_8_hayya.ui.model.Album

@Composable
fun AlbumCard(
    modifier: Modifier = Modifier,
    album: Album,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(200.dp)
            .width(200.dp)
            .clickable(onClick = onClick)
            .border(width = 0.2.dp, color = Color(0xFFA6A07A), shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {

        }
    }
}

@Preview
@Composable
fun AlbumCardPreview() {
    AlbumCard(R.drawable.ic_launcher_background, "Sob Rock", 2021, "Rock")
}