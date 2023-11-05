package com.barian.animeku

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anime (
    val title: String,
    val poster: String,
    val rating: Double,
    val status: String,
    val release: String,
    val genres: String,
    val episodes: String,
    val studios: String,
    val synopsis: String
): Parcelable
