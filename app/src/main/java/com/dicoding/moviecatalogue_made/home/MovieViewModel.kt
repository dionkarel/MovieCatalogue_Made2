package com.dicoding.moviecatalogue_made.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.usecase.CatalogueUseCase

class MovieViewModel(private val catalogueUseCase: CatalogueUseCase) : ViewModel() {
    fun getMovie(sort: String): LiveData<Resource<List<Movie>>> =
        catalogueUseCase.getAllMovie(sort).asLiveData()
}