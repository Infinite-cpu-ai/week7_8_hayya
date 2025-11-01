package com.hanzelius.week7_8_hayya.ui.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
            AsyncImage(
                model = album.albumThumb,
                contentDescription = "album thumbnail",
                modifier = Modifier.height(145.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .size(160.dp),
                contentScale = ContentScale.Crop
            )
            Column (
                modifier = modifier.padding(vertical = 12.dp)
                    .padding(top = 12.dp)
            ){
                Text(
                    text = album.albumName,
                    fontSize = 14.sp,
                    color = Color(0xFFC3BCA8),
                    maxLines = 1
                )
                Text(
                    text = "${album.albumYear} • ${album.albumGenre}",
                    fontSize = 12.sp,
                    color = Color(0xFFC3BCA8)
                )
            }
        }
    }
}