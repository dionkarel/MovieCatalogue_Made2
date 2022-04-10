package com.dicoding.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var title: String,
    var overview: String,
    var popularity: String,
    var releaseDate: String,
    var voteAverage: Double,
    var posterPath: String,
    var backdropPath: String,
    var isFavorite: Boolean
): Parcelable
