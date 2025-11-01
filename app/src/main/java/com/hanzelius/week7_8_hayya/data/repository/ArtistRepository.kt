package com.hanzelius.week7_8_hayya.data.repository

import com.hanzelius.week7_8_hayya.data.service.ArtistService
import com.hanzelius.week7_8_hayya.ui.model.Album
import com.hanzelius.week7_8_hayya.ui.model.Artist
import com.hanzelius.week7_8_hayya.ui.model.Track

class ArtistRepository(private val service: ArtistService) {

    suspend fun getArtist(artistName: String): Artist {
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

        return Artist(
            artistId = artistData.idArtist.toInt(),
            artistName = artistData.strArtist,
            artistGenre = artistData.strGenre,
            artistThumb = artistData.strArtistThumb,
            artistBiography = artistData.strBiographyEN,
            isError = false,
            errorMessage = ""
        )
    }

    suspend fun getAlbum(artistName: String): List<Album> {
        val response = service.searchAlbum(artistName = artistName)
        if (!response.isSuccessful) {
            return emptyList()
        }

        val body = response.body()
        val albums = body?.album?.map { albumData ->
            Album(
                albumId = albumData.idAlbum.toIntOrNull() ?: 0,
                albumName = albumData.strAlbum ?: "",
                albumThumb = albumData.strAlbumThumb ?: "",
                albumYear = albumData.intYearReleased ?: "",
                albumGenre = albumData.strGenre ?: "",
                albumDescription = albumData.strDescriptionEN ?: "",
                artistId = albumData.idArtist.toIntOrNull() ?: 0,
                isError = false,
                errorMessage = ""
            )
        } ?: emptyList()

        return albums
    }

    suspend fun getTrack(albumId: Int): List<Track> {
        val response = service.albumTracks(albumId = albumId)
        if (!response.isSuccessful) {
            return emptyList()
        }

        val body = response.body()
        val tracks = body?.track?.map { trackData ->
            Track(
                trackId = trackData.idTrack.toIntOrNull() ?: 0,
                trackName = trackData.strTrack ?: "",
                trackDuration = (trackData.intDuration?.toIntOrNull() ?: 0),
                isError = false,
                errorMessage = ""
            )
        } ?: emptyList()

        return tracks
    }
}
