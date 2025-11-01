package com.hanzelius.week7_8_hayya.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hanzelius.week7_8_hayya.ui.model.Album
import com.hanzelius.week7_8_hayya.ui.model.Track
import com.hanzelius.week7_8_hayya.ui.viewmodel.ArtistViewModel

@Composable
fun AlbumListView(
    modifier: Modifier = Modifier,
    albumDisplayed: Album,
    tracksDisplayed: List<Track>,
    viewModel: ArtistViewModel
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(15.dp)
    ) {
        item {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .border(
                        width = 0.2.dp,
                        color = Color(0xFFA6A07A),
                        shape = RoundedCornerShape(15.dp)
                    ),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2B2B2B)
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = albumDisplayed.albumThumb,
                        contentDescription = "album cover",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier.height(10.dp))

                    Column(
                        modifier = modifier
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = albumDisplayed.albumName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFC3BCA8),
                            modifier = modifier.padding(bottom = 5.dp)
                        )
                        Text(
                            text = "${albumDisplayed.albumYear} • ${albumDisplayed.albumGenre}",
                            fontSize = 14.sp,
                            color = Color(0xFFC3BCA8),
                            modifier = modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = albumDisplayed.albumDescription,
                            fontSize = 14.sp,
                            color = Color(0xFFC3BCA8),
                            maxLines = 7
                        )
                    }
                }
            }
            Spacer(modifier = modifier.height(20.dp))
        }
        item {
            Text(
                text = "Tracks",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFD1B34B),
            )
            Spacer(modifier = modifier.height(15.dp))
        }
        itemsIndexed(items = tracksDisplayed) { index, track ->
            TrackCard(index = index, track = track, viewModel = viewModel)
        }
    }
}

