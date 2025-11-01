package com.hanzelius.week7_8_hayya.ui.model

data class Artist (
    val artistId: Int = 0,
    val artistName: String = "",
    val artistGenre: String = "",
    val artistThumb: String = "",
    val artistBiography: String = "",

    val isError: Boolean = false,
    val errorMessage: String? = null
)