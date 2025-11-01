package com.hanzelius.week7_8_hayya.ui.viewmodel

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanzelius.week7_8_hayya.data.container.ArtistContainer
import com.hanzelius.week7_8_hayya.data.repository.ArtistRepository
import com.hanzelius.week7_8_hayya.ui.model.Album
import com.hanzelius.week7_8_hayya.ui.model.Artist
import com.hanzelius.week7_8_hayya.ui.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.ceil

class ArtistViewModel(
    private val repository: ArtistRepository = ArtistContainer().artistRepository
) : ViewModel() {

    private val _artist = MutableStateFlow(Artist())
    val artist: StateFlow<Artist> = _artist

    private val _album = MutableStateFlow<List<Album>>(emptyList())
    val album: StateFlow<List<Album>> = _album

    private val _track = MutableStateFlow<List<Track>>(emptyList())
    val track: StateFlow<List<Track>> = _track

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun formatTrackDuration(durationInt: Int): String {
        val secondsTotal = if (durationInt > 1000) durationInt / 1000 else durationInt
        val minutes = secondsTotal / 60
        val seconds = secondsTotal % 60
        return "%d:%02d".format(minutes, seconds)
    }

    fun calculateGridHeight(albumCount: Int): Dp {
        val column = 2
        val heightPerCard = 220.dp
        val spacing = 12.dp
        val row = ceil(albumCount / column.toFloat()).toInt()
        val totalHeight: Dp = (heightPerCard * row) + (spacing * (row - 1))
        return totalHeight
    }

    fun getArtist(artistName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val artistResponse = repository.getArtist(artistName)
                _artist.value = artistResponse
                getAlbum(artistName)
            } catch (e: Exception) {
                when (e) {
                    is IOException -> {
                        _artist.value = Artist(
                            isError = true,
                            errorMessage = "Tidak ada koneksi internet."
                        )
                    }

                    is HttpException -> {
                        _artist.value = Artist(
                            isError = true,
                            errorMessage = "Error: ${e.message()}"
                        )
                    }

                    else -> {
                        _artist.value = Artist(
                            isError = true,
                            errorMessage = e.localizedMessage ?: "Unknown error"
                        )
                    }
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getAlbum(artistName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val albumResponse: List<Album> = repository.getAlbum(artistName)
                _album.value = albumResponse
            } catch (e: Exception) {
                // keep album list empty on error, with simple classification
                _album.value = when (e) {
                    is IOException, is HttpException -> emptyList()
                    else -> emptyList()
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getTrack(albumId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val trackResponse: List<Track> = repository.getTrack(albumId)
                _track.value = trackResponse
            } catch (e: Exception) {
                val errorTrack = when (e) {
                    is IOException -> Track(
                        trackId = 0,
                        trackName = "",
                        trackDuration = 0,
                        isError = true,
                        errorMessage = "Tidak ada koneksi internet."
                    )

                    is HttpException -> Track(
                        trackId = 0,
                        trackName = "",
                        trackDuration = 0,
                        isError = true,
                        errorMessage = e.message ?: "Gagal memuat lagu."
                    )

                    else -> Track(
                        trackId = 0,
                        trackName = "",
                        trackDuration = 0,
                        isError = true,
                        errorMessage = e.localizedMessage ?: "Unknown error"
                    )
                }
                _track.value = listOf(errorTrack)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
