package com.dicoding.moviecatalogue_made.fav.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.usecase.CatalogueUseCase

class FavMovieViewModel(private val catalogueUseCase: CatalogueUseCase) : ViewModel() {
    fun getFavMovie(): LiveData<List<Movie>> =
        catalogueUseCase.getFavMovie().asLiveData()
}