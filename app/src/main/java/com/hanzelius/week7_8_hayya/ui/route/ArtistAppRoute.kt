package com.hanzelius.week7_8_hayya.ui.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hanzelius.week7_8_hayya.ui.view.AlbumListView
import com.hanzelius.week7_8_hayya.ui.view.ErrorView
import com.hanzelius.week7_8_hayya.ui.view.LoadingView
import com.hanzelius.week7_8_hayya.ui.view.MainView
import com.hanzelius.week7_8_hayya.ui.viewmodel.ArtistViewModel

enum class ArtistScreen(
    val title: String,
) {
    Main(title = "Home"),
    AlbumList(title = "Album Detail"),
    Loading(title = "Loading"),
    Error(title = "Error")
}

@Composable
fun ArtistAppRoute() {
    val viewModel: ArtistViewModel = viewModel()
    val navController = rememberNavController()

    val artist by viewModel.artist.collectAsState()
    val albums by viewModel.album.collectAsState()
    val tracks by viewModel.track.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRouteName = currentDestination?.route?.substringBefore("/")
    val currentView = ArtistScreen.entries.find { it.name == currentRouteName }

    Scaffold(
        topBar = {
            MyTopAppBar(
                currentView = currentView,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = ArtistScreen.Main.name
        ) {
            composable(route = ArtistScreen.Main.name) {
                MainView(
                    navController = navController,
                    artistViewModel = viewModel
                )
            }

            composable("${ArtistScreen.AlbumList.name}/{albumId}") { backStackEntry ->
                val albumId = backStackEntry.arguments?.getString("albumId")?.toIntOrNull()

                if (albumId == null) {
                    ErrorView(errorMessage = "Album Invalid.")
                    return@composable
                }

                val selectedAlbum = albums.find { it.albumId == albumId }

                LaunchedEffect(albumId) {
                    viewModel.getTrack(albumId)
                }

                if (selectedAlbum != null) {
                    AlbumListView(
                        albumDisplayed = selectedAlbum,
                        tracksDisplayed = tracks,
                        viewModel = viewModel
                    )
                } else {
                    if (isLoading) {
                        LoadingView()
                    } else {
                        ErrorView(errorMessage = "Album Tidak Ditemukan.")
                    }
                }
            }

            composable(route = ArtistScreen.Loading.name) {
                LoadingView()
            }

            composable(route = ArtistScreen.Error.name) {
                ErrorView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentView: ArtistScreen?,
    navigateUp: () -> Unit,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    viewModel: ArtistViewModel
) {
    val artist by viewModel.artist.collectAsState()
    val selectedName by viewModel.selectedAlbumName.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState()

    val artistName = artist?.artistName ?: "Artist"
    val artistIsError = artist?.isError == true

    CenterAlignedTopAppBar(
        title = {
            Text(
                when {
                    isLoading -> "Loading..."
                    artistIsError -> "Error"
                    currentView == ArtistScreen.Main -> artistName
                    currentView == ArtistScreen.AlbumList -> selectedName ?: artistName
                    currentView != null -> currentView.title
                    else -> artistName
                }
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color(0xFFC3BCA8)
        ),
        modifier = modifier
    )
}
