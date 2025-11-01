package com.hanzelius.week7_8_hayya.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val scroll = rememberScrollState()

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(15.dp)
            .verticalScroll(scroll)
    ){
        Card (
            modifier = modifier.fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF2B2B2B)
            )
        ){
            Column {
                AsyncImage(
                    model = albumDisplayed.albumThumb,
                    contentDescription = "album cover",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier.height(10.dp))

                Column (
                    modifier = modifier.padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                ){
                    Text(
                        text = albumDisplayed.albumName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC3BCA8),
                        modifier = modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = "by ${albumDisplayed.albumYear} • ${albumDisplayed.albumGenre}",
                        fontSize = 14.sp,
                        color = Color(0xFFC3BCA8),
                        modifier = modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = albumDisplayed.albumDescription,
                        fontSize = 14.sp,
                        color = Color(0xFFC3BCA8)
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(20.dp))
        Text(
            text = "Tracks",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFD1B34B),
        )
        Spacer(modifier = modifier.height(15.dp))

        tracksDisplayed.forEachIndexed { index, track ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFA6A07A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        color = Color(0xFF282828),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = modifier.width(12.dp))

                Text(
                    text = track.trackName,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
//
//                val durationInt = track.trackDuration
//                val secondsTotal = if (durationInt > 1000) durationInt / 1000 else durationInt
//                val minutes = secondsTotal / 60
//                val seconds = secondsTotal % 60
//                val formattedDuration = "%d:%02d".format(minutes, seconds)

                Text(
                    text = viewModel.formatTrackDuration(track.trackDuration),
                    color = Color(0xFFA6A07A),
                    fontSize = 14.sp
                )
            }

            Divider(
                color = Color(0xFF3A3A3A),
                thickness = 1.dp
            )
        }
    }
}

