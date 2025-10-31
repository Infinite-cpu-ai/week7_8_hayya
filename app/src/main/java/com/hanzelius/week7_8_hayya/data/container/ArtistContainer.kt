package com.hanzelius.week7_8_hayya.data.container

import com.google.gson.GsonBuilder
import com.hanzelius.week7_8_hayya.data.repository.ArtistRepository
import com.hanzelius.week7_8_hayya.data.service.ArtistService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistContainer {
    companion object{
        val BASE_URL = "https://www.theaudiodb.com"
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .build()

    private val artistService: ArtistService by lazy {
        retrofit.create(ArtistService::class.java)
    }

    public val artistRepository: ArtistRepository by lazy {
        ArtistRepository(artistService)
    }
}