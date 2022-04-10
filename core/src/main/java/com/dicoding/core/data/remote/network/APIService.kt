package com.dicoding.core.data.remote.network

import com.dicoding.core.BuildConfig
import com.dicoding.core.data.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("movie")
    suspend fun getAllMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ListMovieResponse
}