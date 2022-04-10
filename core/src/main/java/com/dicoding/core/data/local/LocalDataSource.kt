package com.dicoding.core.data.local

import com.dicoding.core.data.local.entity.MovieEntity
import com.dicoding.core.data.local.room.CatalogueDAO
import com.dicoding.core.utils.SortUtils
import com.dicoding.core.utils.SortUtils.MOVIE_ENTITY
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mCatalogueDAO: CatalogueDAO) {

    fun getAllMovie(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortQuery(sort, MOVIE_ENTITY)
        return mCatalogueDAO.getAllMovie(query)
    }

    fun getFavMovie(): Flow<List<MovieEntity>> {
        return mCatalogueDAO.getFavMovie()
    }

    fun setFavMovie(movie: MovieEntity, state: Boolean) {
        movie.isFavorite = state
        mCatalogueDAO.updateFavMovie(movie)
    }

    suspend fun insertMovie(movie: List<MovieEntity>) = mCatalogueDAO.insertMovie(movie)

}