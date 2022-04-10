package com.dicoding.moviecatalogue_made.fav.di

import com.dicoding.moviecatalogue_made.fav.favorite.FavMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavMovieViewModel(get()) }
}