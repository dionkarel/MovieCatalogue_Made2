package com.dicoding.moviecatalogue_made.detail

import androidx.lifecycle.ViewModel
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.usecase.CatalogueUseCase

class DetailViewModel (private val catalogueUseCase: CatalogueUseCase) : ViewModel() {
    fun setFavMovie(movie: Movie, state: Boolean) = catalogueUseCase.setFavMovie(movie, state)
}