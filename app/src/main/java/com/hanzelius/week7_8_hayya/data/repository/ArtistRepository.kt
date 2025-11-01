package com.hanzelius.week7_8_hayya.data.repository

import androidx.compose.ui.util.trace
import com.hanzelius.week7_8_hayya.data.service.ArtistService
import com.hanzelius.week7_8_hayya.ui.model.Album
import com.hanzelius.week7_8_hayya.ui.model.Artist
import com.hanzelius.week7_8_hayya.ui.model.Track
import kotlin.Int

class ArtistRepository(private val service: ArtistService) {

    suspend fun getArtist(artistName: String): Artist {
        val artists = service.searchArtist(
            artistName = artistName
        )
        val body = artists.body()!!
        val artistData = body.artists.firstOrNull()!!

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
    suspend fun getAlbum(artistName: String): Album {
        val albums = service.searchAlbum(
            artistName = artistName
        )
        val body = albums.body()!!
        val albumData = body.album.firstOrNull()!!

        return Album(
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
    }
    suspend fun getTrack(albumId: Int): Track {
        val tracks = service.albumTracks(
            albumId = albumId
        )
        val body = tracks.body()!!
        val trackData = body.track.firstOrNull()!!

        return Track(
            trackId = trackData.idTrack.toInt(),
            trackName = trackData.strTrack,
            trackDuration = trackData.intDuration.toInt(),

            isError = false,
            errorMessage = ""
        )
    }
}