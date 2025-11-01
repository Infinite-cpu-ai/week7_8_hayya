package com.hanzelius.week7_8_hayya.data.service

import com.hanzelius.week7_8_hayya.data.dto.AlbumDetailResponse
import com.hanzelius.week7_8_hayya.data.dto.AlbumResponse
import com.hanzelius.week7_8_hayya.data.dto.ArtistResponse
import com.hanzelius.week7_8_hayya.data.dto.TrackResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistService {
    @GET("api/v1/json/123/search.php")
    suspend fun searchArtist(
        @Query("s") artistName: String
    ): Response<ArtistResponse>

    @GET("api/v1/json/123/searchalbum.php")
    suspend fun searchAlbum(
        @Query("s") artistName: String
    ): Response<AlbumResponse>

    @GET("api/v1/json/123/album.php")
    suspend fun albumDetail(
        @Query("m") albumId: Int
    ): Response<AlbumDetailResponse>

    @GET("api/v1/json/123/track.php")
    suspend fun albumTracks(
        @Query("m") albumId: Int
    ): Response<TrackResponse>
}