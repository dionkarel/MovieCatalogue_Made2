package com.dicoding.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: String,
    @field:SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @field:SerializedName("backdrop_path")
    val backdropPath: String,
)