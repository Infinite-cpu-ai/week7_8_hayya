package com.hanzelius.week7_8_hayya.data.service

import com.hanzelius.week7_8_hayya.data.dto.Artist
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistService {
    @GET("api/v1/json/123/search.php")
    suspend fun getCurrentArtist(
        @Query("s") artist: String,
    ): Response<Artist>
}