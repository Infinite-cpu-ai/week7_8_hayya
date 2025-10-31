package com.hanzelius.week7_8_hayya.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hanzelius.week7_8_hayya.ui.view.AlbumDetailView
import com.hanzelius.week7_8_hayya.ui.view.AlbumListView
import com.hanzelius.week7_8_hayya.ui.view.MainView
import com.hanzelius.week7_8_hayya.ui.viewmodel.ArtistViewModel

enum class ArtistScreen {
    Main,
    AlbumList,
    AlbumDetail
}

@Composable
fun ArtistApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val viewModel: ArtistViewModel = viewModel(factory = ArtistViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = ArtistScreen.Main.name,
        modifier = modifier
    ) {
        composable(route = ArtistScreen.Main.name) {
            MainView(
                artistUiState = viewModel.artistUiState,
                retryAction = viewModel::getArtist,
                onArtistClick = { artist ->
                    viewModel.getAlbum(artist.strArtist)
                    navController.navigate(ArtistScreen.AlbumList.name)
                }
            )
        }

        composable(route = ArtistScreen.AlbumList.name) {
            AlbumListView(
                albumUiState = viewModel.albumUiState,
                retryAction = { /* Handled by previous screen */ },
                onAlbumClick = { album ->
                    viewModel.getAlbumDetail(album.idAlbum)
                    navController.navigate(ArtistScreen.AlbumDetail.name)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = ArtistScreen.AlbumDetail.name) {
            AlbumDetailView(
                albumDetailUiState = viewModel.albumDetailUiState,
                retryAction = { /* Handled by previous screen */ },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
