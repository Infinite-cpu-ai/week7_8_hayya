package com.hanzelius.week7_8_hayya.ui.model

data class Track (
    val trackId: Int = 0,
    val trackName: String? = null,
    val trackDuration: Int = 0,

    val isError: Boolean = false,
    val errorMessage: String? = null
)