package com.hanzelius.week7_8_hayya.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hanzelius.week7_8_hayya.ui.model.Track
import com.hanzelius.week7_8_hayya.ui.viewmodel.ArtistViewModel

@Composable
fun TrackCard(
    modifier: Modifier = Modifier,
    index: Int,
    track: Track,
    viewModel: ArtistViewModel
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(35.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF453B25)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        color = Color(0xFFD1B34B),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = track.trackName,
                    color = Color(0xFFA6A07A),
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f),
                    maxLines = 1
                )

                Text(
                    text = viewModel.formatTrackDuration(track.trackDuration),
                    color = Color(0xFFA6A07A),
                    fontSize = 14.sp
                )
            }
            Divider(color = Color(0xFF3A3A3A), thickness = 1.dp)
        }
    }
}
