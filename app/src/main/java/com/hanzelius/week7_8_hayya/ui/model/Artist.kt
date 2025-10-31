package com.hanzelius.week7_8_hayya.ui.model

data class Artist (
    val artistId: Int = 0,
    val artistName: String? = null,
    val artistGenre: String? = null,
    val artistThumb: String? = null,
    val artistBiography: String? = null,

    val isError: Boolean = false,
    val errorMessage: String? = null
)