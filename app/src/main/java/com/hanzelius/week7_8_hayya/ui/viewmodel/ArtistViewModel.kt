package com.hanzelius.week7_8_hayya.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanzelius.week7_8_hayya.data.container.ArtistContainer
import com.hanzelius.week7_8_hayya.ui.model.Album
import com.hanzelius.week7_8_hayya.ui.model.Artist
import com.hanzelius.week7_8_hayya.ui.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ArtistViewModel() : ViewModel() {

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

    fun getArtist(artistName : String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val artistResponse = ArtistContainer().artistRepository.getArtist(artistName)
                _artist.value = artistResponse
                getAlbum(artistName)
            } catch (e: IOException) {
                _artist.value = Artist(
                    isError = true,
                    errorMessage = "Tidak ada koneksi internet."
                )
            } catch (e: HttpException) {
                _artist.value = Artist(
                    isError = true,
                    errorMessage = "Error: ${e.message()}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getAlbum(artistName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val albumResponse = ArtistContainer().artistRepository.getAlbum(artistName)
                _album.value = listOf(albumResponse)
            } catch (e: IOException) {
                _album.value = emptyList()
            } catch (e: HttpException) {
                _album.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun getTrack(albumId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val trackResponse = ArtistContainer().artistRepository.getTrack(albumId)
                _track.value = listOf(trackResponse)
            } catch (e: IOException) {
                _track.value = listOf(
                    Track(isError = true, errorMessage = "Tidak ada koneksi internet.")
                )
            } catch (e: HttpException) {
                _track.value = listOf(
                    Track(isError = true, errorMessage = e.message ?: "Gagal memuat lagu.")
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}
