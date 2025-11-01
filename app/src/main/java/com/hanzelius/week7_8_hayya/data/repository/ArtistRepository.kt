package com.hanzelius.week7_8_hayya.data.repository

import com.hanzelius.week7_8_hayya.data.service.ArtistService
import com.hanzelius.week7_8_hayya.ui.model.Album
import com.hanzelius.week7_8_hayya.ui.model.Artist
import com.hanzelius.week7_8_hayya.ui.model.Track

class ArtistRepository(private val service: ArtistService) {

    suspend fun getArtist(artistName: String): Artist {
        return try {
            val response = service.searchArtist(artistName = artistName)
            if (!response.isSuccessful) {
                return Artist(
                    artistId = 0,
                    artistName = "",
                    artistGenre = "",
                    artistThumb = "",
                    artistBiography = "",
                    isError = true,
                    errorMessage = "HTTP ${response.code()}: ${response.message()}"
                )
            }

            val body = response.body()
            val artistData = body?.artists?.firstOrNull()
            if (artistData == null) {
                return Artist(
                    artistId = 0,
                    artistName = "",
                    artistGenre = "",
                    artistThumb = "",
                    artistBiography = "",
                    isError = true,
                    errorMessage = "Artist not found"
                )
            }

            Artist(
                artistId = artistData.idArtist.toInt(),
                artistName = artistData.strArtist,
                artistGenre = artistData.strGenre,
                artistThumb = artistData.strArtistThumb,
                artistBiography = artistData.strBiographyEN,
                isError = false,
                errorMessage = ""
            )
        } catch (e: Exception) {
            Artist(
                artistId = 0,
                artistName = "",
                artistGenre = "",
                artistThumb = "",
                artistBiography = "",
                isError = true,
                errorMessage = e.localizedMessage ?: "Unknown error"
            )
        }
    }

    suspend fun getAlbum(artistName: String): Album {
        return try {
            val response = service.searchAlbum(artistName = artistName)
            if (!response.isSuccessful) {
                return Album(
                    albumId = 0,
                    albumName = "",
                    albumThumb = "",
                    albumYear = "",
                    albumGenre = "",
                    albumDescription = "",
                    artistId = 0,
                    isError = true,
                    errorMessage = "HTTP ${response.code()}: ${response.message()}"
                )
            }

            val body = response.body()
            val albumData = body?.album?.firstOrNull()
            if (albumData == null) {
                return Album(
                    albumId = 0,
                    albumName = "",
                    albumThumb = "",
                    albumYear = "",
                    albumGenre = "",
                    albumDescription = "",
                    artistId = 0,
                    isError = true,
                    errorMessage = "Album not found"
                )
            }

            Album(
                albumId = albumData.idAlbum.toInt(),
                albumName = albumData.strAlbum,
                albumThumb = albumData.strAlbumThumb,
                albumYear = albumData.intYearReleased,
                albumGenre = albumData.strGenre,
                albumDescription = albumData.strDescriptionEN,
                artistId = albumData.idArtist.toInt(),
                isError = false,
                errorMessage = ""
            )
        } catch (e: Exception) {
            Album(
                albumId = 0,
                albumName = "",
                albumThumb = "",
                albumYear = "",
                albumGenre = "",
                albumDescription = "",
                artistId = 0,
                isError = true,
                errorMessage = e.localizedMessage ?: "Unknown error"
            )
        }
    }

    suspend fun getTrack(albumId: Int): Track {
        return try {
            val response = service.albumTracks(albumId = albumId)
            if (!response.isSuccessful) {
                return Track(
                    trackId = 0,
                    trackName = "",
                    trackDuration = 0,
                    isError = true,
                    errorMessage = "HTTP ${response.code()}: ${response.message()}"
                )
            }

            val body = response.body()
            val trackData = body?.track?.firstOrNull()
            if (trackData == null) {
                return Track(
                    trackId = 0,
                    trackName = "",
                    trackDuration = 0,
                    isError = true,
                    errorMessage = "Track not found"
                )
            }

            Track(
                trackId = trackData.idTrack.toInt(),
                trackName = trackData.strTrack,
                trackDuration = trackData.intDuration.toInt(),
                isError = false,
                errorMessage = ""
            )
        } catch (e: Exception) {
            Track(
                trackId = 0,
                trackName = "",
                trackDuration = 0,
                isError = true,
                errorMessage = e.localizedMessage ?: "Unknown error"
            )
        }
    }
}
