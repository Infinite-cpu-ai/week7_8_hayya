package com.hanzelius.week7_8_hayya.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hanzelius.week7_8_hayya.model.Artist
import com.hanzelius.week7_8_hayya.ui.viewmodel.ArtistUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    artistUiState: ArtistUiState,
    retryAction: () -> Unit,
    onArtistClick: (Artist) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Artist Explorer") })
        }
    ) { padding ->
        when (artistUiState) {
            is ArtistUiState.Loading -> LoadingView()
            is ArtistUiState.Success -> ArtistListView(
                artists = artistUiState.artists,
                onArtistClick = onArtistClick,
                modifier = Modifier.padding(padding)
            )
            is ArtistUiState.Error -> ErrorView(retryAction = retryAction)
        }
    }
}

@Composable
fun ArtistListView(
    artists: List<Artist>,
    onArtistClick: (Artist) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(artists) { artist ->
            ArtistCard(artist = artist, onClick = { onArtistClick(artist) })
        }
    }
}
