package com.dicoding.core.utils

import com.dicoding.core.data.local.entity.MovieEntity
import com.dicoding.core.data.remote.response.MovieDetailResponse
import com.dicoding.core.domain.model.Movie

object DataMapper {
    fun mapMovieResponsesToEntities(input: List<MovieDetailResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                popularity = it.popularity,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                popularity = it.popularity,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                isFavorite = it.isFavorite
            )
        }

    fun mapMovieDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        overview = input.overview,
        popularity = input.popularity,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        backdropPath = input.backdropPath,
        posterPath = input.posterPath,
        isFavorite = input.isFavorite
    )
}