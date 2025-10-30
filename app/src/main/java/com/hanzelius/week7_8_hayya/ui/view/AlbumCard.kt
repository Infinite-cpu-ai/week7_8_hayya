package com.hanzelius.week7_8_hayya.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanzelius.week7_8_hayya.ui.model.Album

@Composable
fun AlbumCard(
    img: Int,
    desc: String,
    date: Int,
    genre: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(200.dp)
            .width(200.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize()

        ) {

        }
    }
}

@Preview
@Composable
fun AlbumCardPreview() {
    AlbumCard()
}