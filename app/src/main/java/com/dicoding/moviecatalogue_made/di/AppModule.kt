package com.dicoding.moviecatalogue_made.di

import com.dicoding.core.domain.usecase.CatalogueInteractor
import com.dicoding.core.domain.usecase.CatalogueUseCase
import com.dicoding.moviecatalogue_made.detail.DetailViewModel
import com.dicoding.moviecatalogue_made.home.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<CatalogueUseCase> { CatalogueInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}