package com.hanzelius.week7_8_hayya.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.hanzelius.week7_8_hayya.ui.route.ArtistScreen
import com.hanzelius.week7_8_hayya.ui.viewmodel.ArtistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    artistViewModel: ArtistViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {
    val artistDisplayed by artistViewModel.artist.collectAsState()
    val albumDisplayed by artistViewModel.album.collectAsState()

    val isLoading by artistViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        artistViewModel.getArtist("Coldplay")
    }

    when {
        isLoading -> LoadingView()
        artistDisplayed.isError -> ErrorView(
            errorMessage = artistDisplayed.errorMessage ?: "Terjadi kesalahan tak terduga.",
            onRetry = { artistViewModel.getArtist(artistName = "Coldplay") }
        )

        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color(0xFF282828))
            ) {
                item {
                    Row(
                        modifier = modifier
                            .padding(top = 8.dp, bottom = 12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box {
                            Image(
                                painter = rememberAsyncImagePainter(artistDisplayed.artistThumb),
                                contentDescription = "Artist Thumbnail",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xFF3C3C3C),
                                        RoundedCornerShape(15.dp)
                                    )
                                    .size(350.dp)
                            )
                            Column(
                                modifier = modifier
                                    .align(Alignment.BottomStart)
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = artistDisplayed.artistName,
                                    fontSize = 20.sp,
                                    color = Color(0xFFC3BCA8)
                                )
                                Text(
                                    text = artistDisplayed.artistGenre,
                                    color = Color(0xFFC3BCA8),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                    Column(
                        modifier = modifier
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Albums",
                            color = Color(0xFFC3BCA8)
                        )
                        Spacer(modifier = modifier.height(15.dp))
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            userScrollEnabled = false,
                            modifier = modifier.height(
                                artistViewModel.calculateGridHeight(
                                    albumDisplayed.size
                                )
                            )
                        ) {
                            items(albumDisplayed) { album ->
                                AlbumCard(
                                    album = album,
                                    modifier = modifier,
                                    onClick = {
                                        navController.navigate("${ArtistScreen.AlbumList.name}/${album.albumId}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}