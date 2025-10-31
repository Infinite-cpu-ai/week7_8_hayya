package com.hanzelius.week7_8_hayya.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hanzelius.week7_8_hayya.model.Album
import com.hanzelius.week7_8_hayya.ui.viewmodel.AlbumUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumListView(
    albumUiState: AlbumUiState,
    retryAction: () -> Unit,
    onAlbumClick: (Album) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Albums") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (albumUiState) {
            is AlbumUiState.Loading -> LoadingView()
            is AlbumUiState.Success -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(padding)
            ) {
                items(albumUiState.albums) { album ->
                    AlbumCard(album = album, onClick = { onAlbumClick(album) })
                }
            }
            is AlbumUiState.Error -> ErrorView(retryAction = retryAction)
        }
    }
}
