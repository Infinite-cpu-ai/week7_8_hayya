package com.hanzelius.week7_8_hayya.ui.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    val icon: ImageVector? = null
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentView = ArtistScreen.entries.find { it.name == currentDestination?.route}

    Scaffold (
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
                val albums by viewModel.album.collectAsState()
                val tracks by viewModel.track.collectAsState()
                val selectedAlbum = albums.find { it.albumId == albumId }

                LaunchedEffect (albumId) {
                    albumId?.let { viewModel.getTrack(it) }
                }

                if (selectedAlbum != null) {
                    AlbumListView(albumDisplayed = selectedAlbum, tracksDisplayed = tracks)
                } else {
                    ErrorView(errorMessage = "Album Not Found.")
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
    val isLoading by viewModel.isLoading.collectAsState()

    CenterAlignedTopAppBar(
        title = {
            Text(
                if (isLoading)
                    "Loading..."
                else if (artist.isError)
                    "Error"
                else
                    artist.artistName
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF191E1E),
            titleContentColor = Color(0xFF57504A)
        ),
        modifier = modifier
    )
}