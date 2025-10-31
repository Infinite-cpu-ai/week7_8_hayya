package com.hanzelius.week7_8_hayya.ui.model

data class Album(
    val albumId: Int = 0,
    val albumName: String? = null,
    val albumYear: String? = null,
    val albumGenre: String? = null,
    val albumThumb: String? = null,
    val albumDescription: String? = null,

    val artistId: Int = 0, // Foreign key

    val isError: Boolean = false,
    val errorMessage: String? = null
)