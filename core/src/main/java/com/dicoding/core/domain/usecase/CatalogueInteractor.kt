package com.dicoding.core.domain.usecase

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.repository.ICatalogueRepository
import kotlinx.coroutines.flow.Flow

class CatalogueInteractor(private val catalogueRepository: ICatalogueRepository) : CatalogueUseCase {

    override fun getAllMovie(sort: String): Flow<Resource<List<Movie>>> {
        return catalogueRepository.getAllMovie(sort)
    }

    override fun getFavMovie(): Flow<List<Movie>> {
        return catalogueRepository.getFavMovie()
    }

    override fun setFavMovie(movie: Movie, state: Boolean) {
        return catalogueRepository.setFavMovie(movie, state)
    }

}