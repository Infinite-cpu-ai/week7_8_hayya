package com.hanzelius.week7_8_hayya.ui.model

data class Album(
    val albumId: Int = 0,
    val albumName: String = "",
    val albumYear: String = "",
    val albumGenre: String = "",
    val albumThumb: String = "",
    val albumDescription: String = "",

    val artistId: Int = 0, // Foreign key

    val isError: Boolean = false,
    val errorMessage: String? = null
)